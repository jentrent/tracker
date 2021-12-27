package com.jentrent.tracker.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jentrent.tracker.dao.IssueDAO;
import com.jentrent.tracker.dao.IssueDAOImpl;
import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.Assignee;
import com.jentrent.tracker.model.Issue;
import com.jentrent.tracker.model.IssueFilter;
import com.jentrent.tracker.model.Priority;
import com.jentrent.tracker.model.Status;

@Component("IssueService")
public class IssueServiceImpl extends BaseService implements IssueService{

	@Autowired
	private IssueDAO issueDAO = new IssueDAOImpl();

	public Issue createIssue(Issue issue) throws TrackerException{

		Date d = new Date();
		issue.setCreated(d);
		issue.setModified(d);

		if(issue.getStatus() == null){
			issue.setStatus(Status.NEW);
		}

		if(issue.getPriority() == null){
			issue.setPriority(Priority.LOW);
		}

		validate(issue);

		// checkForDuplicateIssue(issue);

		return issueDAO.createIssue(issue);
	}

	public Issue readIssue(Integer issueId){

		return issueDAO.readIssue(issueId);
	}

	public Issue updateIssue(Issue issue) throws TrackerException{

		validate(issue);

		// checkForDuplicateIssue(issue);

		return issueDAO.updateIssue(issue);
	}

	public Assignee replaceAssignee(Integer analystId, Integer accountId){

		return issueDAO.replaceAssignee(analystId, accountId);
	}

	public void deleteIssue(Issue issue){

		issueDAO.deleteIssue(issue);
	}

	public List<Issue> listIssuesForAll(){

		return issueDAO.listIssuesForAll();
	}

	public List<Issue> listIssuesForCreator(Account createdBy){

		return issueDAO.listIssuesForCreator(createdBy);
	}

	public List<Issue> listIssuesForProject(Integer projectId){

		return issueDAO.listIssuesForProject(projectId);
	}

	public List<Issue> listForFilter(IssueFilter filter){

		return issueDAO.listForFilter(filter);
	}

}
