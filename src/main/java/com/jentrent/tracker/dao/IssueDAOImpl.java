package com.jentrent.tracker.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
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

		EntityManager em = null;

		try{

			System.out.println("Assignees: " + issue.getAssignees().size());

			issue.setModified(new Date());

			em = getEm();

			em.getTransaction().begin();

			Query q1 = em.createQuery("select a from Assignee a where a.issue.issueId = :issueId", Assignee.class);
			q1.setParameter("issueId", issue.getIssueId().intValue());

			List<Assignee> existingAssignees = null;

			try{

				System.out.println("Retrieving existing assigees");
				existingAssignees = q1.getResultList();

			}catch(Exception ignored){
			}

			if(existingAssignees != null){

				for(Assignee a: existingAssignees){
					System.out.println("Executing deletion of " + a.getAssigneeId() + ", " + a.getAccount());
					Query delete = em.createQuery("delete from Assignee a where a.assigneeId = :assigneeId");
					delete.setParameter("assigneeId", a.getAssigneeId());
					delete.executeUpdate();
				}

			}

			for(Assignee a: issue.getAssignees()){

				System.out.println("Persisting assigneee account: " + a.getAccount().getEmail());

				getEm().persist(a);
			}

			em.getTransaction().commit();

			em.getTransaction().begin();

			em.merge(issue);

			System.out.println("Issue merged");

			em.getTransaction().commit();

			getEm().clear();

		}catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
			throw e;
		}finally{

		}

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

	public Assignee replaceAssignee(Integer analystId, Integer accountId){

		beginTrx();

		Query q1 = getEm().createQuery("update Assignee a set a.account_id = :accountId where a.analystId = :analystId");
		q1.setParameter("analystId", analystId.intValue());
		q1.setParameter("accountId", accountId.intValue());

		q1.executeUpdate();

		commitTrx();

		TypedQuery<Assignee> q2 = getEm().createQuery("select Assignee a where a.analystId = :analystId", Assignee.class);
		q1.setParameter("analystId", analystId.intValue());

		return q2.getSingleResult();

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

}
