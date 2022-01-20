package com.jentrent.tracker.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

	private List<Project> projects;

	private boolean globalFilterOnly;

	private List<Project> filteredProjects;

	private Boolean isMyProjectsOnly = Boolean.FALSE;

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

		isMyProjectsOnly = !isMyProjectsOnly;

		refreshProjectList();

		return "projectList";
	}

	public String getFilterText(){

		if(getRequestParam("accountId") != null){

			Account account = accountService.readAccount(Integer.parseInt((String) getRequestParam("accountId")));

			return "Showing Projects for " + account.getFirstName() + " " + account.getLastName();

		}else if(isMyProjectsOnly){

			return "Showing Your Projects";
		}else{

			return "Showing All Projects";
		}

	}

	public void refreshProjectList(){

		if(isMyProjectsOnly){

			projects = projectService.listProjectsForAssignee(getAccount());

		}else{

			projects = projectService.listProjectsForAll();
		}

	}

	public String submitRemoveFilter(){

		this.isMyProjectsOnly = Boolean.FALSE;

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

		this.isMyProjectsOnly = Boolean.TRUE;

		return "projectList";

	}

	public String descriptionSummary(Project p){

		if(p.getDescription().length() > 30){

			Document plain = Jsoup.parse(p.getDescription());

			String text = plain.body().text();

			if(text.length() > 30){
				return text.substring(0, 30) + "...";
			}else{
				return text;
			}

		}else{

			Document plain = Jsoup.parse(p.getDescription());

			return plain.body().text();
		}

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
