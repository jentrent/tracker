package com.jentrent.tracker.view;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.Assignee;
import com.jentrent.tracker.model.Issue;
import com.jentrent.tracker.model.IssueFilter;
import com.jentrent.tracker.model.Priority;
import com.jentrent.tracker.model.Project;
import com.jentrent.tracker.model.Status;
import com.jentrent.tracker.model.Type;
import com.jentrent.tracker.service.AccountService;
import com.jentrent.tracker.service.IssueService;
import com.jentrent.tracker.service.ProjectService;

@ManagedBean
@SessionScoped
public class IssueListView extends BaseView implements Serializable {

	private IssueFilter filter;

	private boolean globalFilterOnly;
	
	private Boolean isMyIssuesOnly = Boolean.FALSE;

	private List<Issue> issues;

	private List<Issue> filteredIssues;

	@Autowired
	private IssueService issueService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ProjectService projectService;

	@PostConstruct
	public void init() {

		super.init();

		globalFilterOnly = false;

		filter = new IssueFilter();

		if (getRequestParam("projectId") != null) {

			String projectId = (String) getRequestParam("projectId");

			issues = issueService.listIssuesForProject(Integer.parseInt(projectId));

		}

		if (getRequestParam("accountId") != null) {

			Integer accountId = Integer.parseInt((String) getRequestParam("accountId"));

			IssueFilter filter = new IssueFilter();
			filter.setCreatedByAccountId(accountId);
			filter.setAssigneeAccountId(accountId);

			issues = issueService.listForFilter(filter);

		}

	}

	public String submitFilter() {

		issues = issueService.listForFilter(filter);

		return "issueList";

	}

	public String submitFilterForMyIssues() {

		filter.setAssigneeAccountId(getAccount().getAccountId());
		filter.setCreatedByAccountId(getAccount().getAccountId());

		issues = issueService.listForFilter(filter);
		
		isMyIssuesOnly = Boolean.TRUE;

		return "issueList";

	}

	public String submitRemoveFilter() {

		issues = issueService.listIssuesForAll();
		filter = new IssueFilter();
		
		isMyIssuesOnly = Boolean.FALSE;

		return "issueList";

	}

	public String setfilterByProjectId(Integer projectId) {

		filter = new IssueFilter();

		filter.setProjectId(projectId);

		issues = issueService.listForFilter(filter);

		return "issueList";
	}

	public String setfilterByAccountId(Integer accountId) {

		filter = new IssueFilter();

		filter.setAssigneeAccountId(accountId);

		issues = issueService.listForFilter(filter);

		return "issueList";
	}
	
	public String toggleIsMyAccountsOnly(){

		isMyIssuesOnly = !isMyIssuesOnly;

		refreshIssueList();

		return "issueList";
	}

	public String getFilterText() {
		
		if(getRequestParam("accountId") != null){

			Account account = accountService.readAccount(Integer.parseInt((String) getRequestParam("accountId")));

			return "Showing Issues for " + account.getFirstName() + " " + account.getLastName();

		}else if(isMyIssuesOnly){

			return "Showing Your Issues";
		}else{

			return "Showing All Issues";
		}

	}

	public void toggleGlobalFilter() {

		setGlobalFilterOnly(!isGlobalFilterOnly());
	}

	public boolean isGlobalFilterOnly() {

		return globalFilterOnly;
	}

	public void refreshIssueList() {

		issues = issueService.listIssuesForAll();
	}

	public void setFilter(IssueFilter filter) {

		this.filter = filter;
	}

	public IssueFilter getFilter() {

		return filter;
	}

	public List<Account> getAccountsFromAssignees(List<Assignee> assignees) {

		List<Account> accounts = new LinkedList<Account>();

		for (Assignee a : assignees) {
			Account account = accountService.readAccount(a.getAccount().getAccountId());
			accounts.add(account);
		}

		return accounts;
	}

	public List<Issue> getIssues() {

		if (issues == null) {
			issues = issueService.listIssuesForAll();
		}

		return issues;
	}

	public Type[] getTypes() {

		return Type.values();
	}

	public Status[] getStatuses() {

		return Status.values();
	}

	public Priority[] getPriorities() {

		return Priority.values();
	}

	public List<Issue> getFilteredIssues() {

		return filteredIssues;
	}

	public List<Project> getProjects() {

		return projectService.listProjectsForAll();
	}

	public void setFilteredIssues(List<Issue> filteredIssues) {

		this.filteredIssues = filteredIssues;
	}

	public void setGlobalFilterOnly(boolean globalFilterOnly) {

		this.globalFilterOnly = globalFilterOnly;
	}

	public void setIssues(List<Issue> issues) {

		this.issues = issues;
	}

}
