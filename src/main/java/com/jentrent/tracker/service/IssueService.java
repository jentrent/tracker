package com.jentrent.tracker.service;

import java.util.List;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.Assignee;
import com.jentrent.tracker.model.Issue;
import com.jentrent.tracker.model.IssueFilter;

public interface IssueService{

	public Issue createIssue(Issue issue) throws TrackerException;

	public Issue readIssue(Integer issueId);

	public Issue updateIssue(Issue issue) throws TrackerException;

	public Assignee replaceAssignee(Integer analystId, Integer accountId);

	public void deleteIssue(Issue issue);

	public List<Issue> listIssuesForAll();

	public List<Issue> listIssuesForCreator(Account createdBy);

	public List<Issue> listIssuesForProject(Integer projectId);

	public List<Issue> listForFilter(IssueFilter filter);

}
