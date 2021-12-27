package com.jentrent.tracker.dao;

import java.util.List;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.Assignee;
import com.jentrent.tracker.model.Issue;
import com.jentrent.tracker.model.IssueFilter;

public interface IssueDAO{

	public Issue createIssue(Issue issue);

	public Issue readIssue(Integer issueId);

	public Issue readIssue(String title);

	public Issue updateIssue(Issue issue);

	public Assignee replaceAssignee(Integer analystId, Integer accountId);

	public void deleteIssue(Issue issue);

	public List<Issue> listIssuesForAll();

	public List<Issue> listIssuesForCreator(Account createdBy);

	public List<Issue> listIssuesForProject(Integer projectId);

	public List<Issue> listForFilter(IssueFilter issueFilter);

}
