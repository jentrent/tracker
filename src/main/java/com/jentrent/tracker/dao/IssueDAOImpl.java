package com.jentrent.tracker.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.Assignee;
import com.jentrent.tracker.model.Issue;
import com.jentrent.tracker.model.IssueFilter;

@Component("IssueDAO")
public class IssueDAOImpl extends BaseDAO implements IssueDAO{

	public Issue createIssue(Issue issue){

		beginTrx();

		getEm().persist(issue);

		commitTrx();

		return issue;
	}

	public Issue readIssue(Integer issueId){

		Query q = getEm().createQuery("select i from Issue i where i.issueId = :issueId");
		q.setParameter("issueId", issueId.intValue());

		return (Issue) q.getSingleResult();
	}

	public Issue readIssue(String title){

		Query q = getEm().createQuery("select i from Issue i where i.title = :title");
		q.setParameter("title", title);

		try{
			return (Issue) q.getSingleResult();
		}catch(NoResultException ignored){
			return null;
		}

	}

	public Issue updateIssue(Issue issue){

		issue.setModified(new Date());

		beginTrx();

		if(issue.getAssignees() != null){

			List<Assignee> list = new LinkedList<Assignee>();
			list.addAll(issue.getAssignees());

			for(Assignee assignee: list){

				if(assignee.getAssigneeId() == null){
					getEm().persist(assignee);
				}else{
					getEm().merge(assignee);
				}

			}

			issue.setAssignees(null);

		}

		getEm().merge(issue);

		commitTrx();

		return readIssue(issue.getIssueId());
	}

	public void deleteIssue(Issue issue){

		if(issue == null){
			return;
		}

		beginTrx();

		Query q1 = getEm().createQuery("delete from Assignee a where a.issue.issueId = :issueId");
		q1.setParameter("issueId", issue.getIssueId().intValue());

		q1.executeUpdate();

		Query q2 = getEm().createQuery("delete from Issue where issueId = :issueId");
		q2.setParameter("issueId", issue.getIssueId().intValue());

		q2.executeUpdate();

		commitTrx();

	}

	public List<Issue> listIssuesForAll(){

		getEm().clear();

		TypedQuery<Issue> q = getEm().createQuery("select i from Issue i order by status, priority, title", Issue.class);
		return q.getResultList();

	}

	public List<Issue> listIssuesForCreator(Account createdBy){

		getEm().clear();

		TypedQuery<Issue> q = getEm().createQuery(
				"select i from Issue i where i.createdBy = :createdBy order by createdBy, status, priority, title", Issue.class);
		q.setParameter("createdBy", createdBy);

		return q.getResultList();

	}

	public List<Issue> listIssuesForProject(Integer projectId){

		getEm().clear();

		TypedQuery<Issue> q = getEm()
				.createQuery("select i from Issue i where i.project.projectId = :projectId order by status, priority, title", Issue.class);
		q.setParameter("projectId", projectId);

		return q.getResultList();
	}

	public List<Issue> listForFilter(IssueFilter filter){

		getEm().clear();

		StringBuffer buf = new StringBuffer();

		buf.append("select i from Issue i where ");

		if(filter.getProjectId() != null){
			buf.append("i.project.projectId = " + filter.getProjectId() + " and ");
		}

		if(filter.getPriority() != null){
			buf.append("i.priority = " + filter.getPriority().decodeToInt() + " and ");
		}

		if(filter.getStatus() != null){
			buf.append("i.status = " + filter.getStatus().decodeToInt() + " and ");
		}

		if(filter.getType() != null){
			buf.append("i.type = " + filter.getType().decodeToInt() + " and ");
		}

//		if(filter.getCreatedByAccountId() != null){
//			buf.append("i.createdBy = " + filter.getCreatedByAccountId() + " and ");
//		}

		if(filter.getText() != null && filter.getText().trim().length() > 0){
			buf.append("(upper(i.title) like upper('%" + filter.getText() + "%') or " + "upper(i.description) like upper('%"
					+ filter.getText() + "%')) and ");
		}

		buf = new StringBuffer(buf.toString().trim());

		if(buf.indexOf("where") == buf.length() - 5){
			buf.delete(buf.indexOf("where"), buf.length());
		}else{

			buf.delete(buf.lastIndexOf("and"), buf.length());
		}

		buf.append("order by status, priority, title");

		TypedQuery<Issue> q = getEm().createQuery(buf.toString(), Issue.class);

		List<Issue> list = q.getResultList();

		List<Issue> toReturn = new LinkedList<Issue>();

		if(filter.getAssigneeAccountId() != null){

			for(Issue i: list){

				for(Assignee assigned: i.getAssignees()){

					if(assigned.getAccount().getAccountId().equals(filter.getAssigneeAccountId())){
						toReturn.add(i);
						break;
					}

				}

			}

		}

		return toReturn;
	}

	public static void main(String[] args){

		IssueDAOImpl dao = new IssueDAOImpl();

		Issue i = dao.readIssue(1);

		List<Assignee> assignees = i.getAssignees();

		System.out.println("assignees: " + assignees.size());

		i.setTitle("New Title");

		dao.updateIssue(i);

		Issue updated = dao.readIssue(1);

		System.out.println("updated: " + updated.getTitle());
		System.out.println("assigneesU: " + updated.getAssignees());

	}

}
