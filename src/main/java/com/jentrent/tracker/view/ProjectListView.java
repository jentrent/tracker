package com.jentrent.tracker.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.Issue;
import com.jentrent.tracker.model.IssueFilter;
import com.jentrent.tracker.model.Project;
import com.jentrent.tracker.service.AccountService;
import com.jentrent.tracker.service.IssueService;
import com.jentrent.tracker.service.ProjectService;

@ManagedBean
@SessionScoped
public class ProjectListView extends BaseView implements Serializable{

	private Boolean isMyAccountsOnly = Boolean.FALSE;

	private List<Project> projects;

	private boolean globalFilterOnly;

	private List<Project> filteredProjects;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private IssueService issueService;

	@Autowired
	private AccountService accountService;

	@PostConstruct
	public void init(){

		super.init();

		globalFilterOnly = false;

		if(getRequestParam("accountId") != null){

			Integer accountId = Integer.parseInt((String) getRequestParam("accountId"));

			Account account = accountService.readAccount(accountId);

			projects = projectService.listProjectsForAssignee(account);

		}

	}

	public String toggleIsMyAccountsOnly(){

		isMyAccountsOnly = !isMyAccountsOnly;

		refreshProjectList();

		return "projectList";
	}

	public String getIsMyAccountsOnlyText(){

		if(isMyAccountsOnly){
			return "Show All Projects";
		}else{
			return "Only My Projects";
		}

	}
	// STILL WORKING LOOK AT ISSUE LIST FOR WORKINNG EXAMPLE
//	public String getFilterText() {
//
//		if (getRequestParam("projectId") != null) {
//
//			Project project = projectService.readProject((Integer) getRequestParam("projectId"));
//
//			return "by Project " + project.getName();
//		} else {
//			return "N/A";
//		}
//
//	}

	public void refreshProjectList(){

		if(isMyAccountsOnly){

			projects = projectService.listProjectsForCreator(getAccount());

		}else{

			projects = projectService.listProjectsForAll();
		}

	}

	public String submitRemoveFilter(){

		projects = projectService.listProjectsForAll();

		return "projectList";

	}

	public String setProjectsByAccountId(Account account){

		projects = projectService.listProjectsForCreator(account);

		return "projectList";
	}

	public void toggleGlobalFilter(){

		setGlobalFilterOnly(!isGlobalFilterOnly());
	}

	public boolean isGlobalFilterOnly(){

		return globalFilterOnly;
	}

	public void setGlobalFilterOnly(boolean globalFilterOnly){

		this.globalFilterOnly = globalFilterOnly;
	}

	public String[] getIsOpen(){

		return new String[] { "Open", "Closed" };
	}

	public String submitFilterForMyProjects(){

		projects = projectService.listProjectsForCreator(getAccount());

		IssueFilter filter = new IssueFilter();
		filter.setAssigneeAccountId(getAccount().getAccountId());

		List<Issue> issues = issueService.listForFilter(filter);

		for(Issue i: issues){

			projects.add(i.getProject());
		}

		return "projectList";

	}

	public List<Project> getFilteredProjects(){

		return filteredProjects;
	}

	public void setFilteredProjects(List<Project> filteredProjects){

		this.filteredProjects = filteredProjects;
	}

	public List<Project> getProjects(){

		if(projects == null){
			projects = projectService.listProjectsForAll();
		}

		return projects;
	}

	public String decodeIsOpen(Project project){

		return project.getIsOpen() ? "Open" : "Closed";
	}

}
