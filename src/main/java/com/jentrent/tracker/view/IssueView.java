package com.jentrent.tracker.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.Assignee;
import com.jentrent.tracker.model.Issue;
import com.jentrent.tracker.model.Project;
import com.jentrent.tracker.model.Role;
import com.jentrent.tracker.service.AccountService;
import com.jentrent.tracker.service.IssueService;
import com.jentrent.tracker.service.ProjectService;
import com.jentrent.tracker.service.TrackerException;

@ManagedBean
@SessionScoped
public class IssueView extends BaseView implements Serializable{

	private Issue issue;

	private Integer developerId;
	private Integer analystId;
	private Integer testerId;
	private Integer sysAdminId;
	private Integer pMId;
	private Integer trackerAdminId;

	private Date date;

	@NotNull(message = "An issue must be assigned to a Project")
	private Integer projectId;

	private Boolean isEditMode = Boolean.FALSE;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private IssueService issueService;

	@Autowired
	private AccountService accountService;

	@PostConstruct
	public void init(){

		super.init();

		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

		if(req.getParameter("issueId") != null){

			String issueId = req.getParameter("issueId");
			setEditModeForUpdate(issueId);

		}else{

			setEditModeForCreate();
		}

	}

	public String setEditModeForCreate(){

		issue = new Issue();
		issue.setCreatedBy(getAccount());

		projectId = null;

		clearAssignees();

		isEditMode = Boolean.FALSE;

		return "issue";
	}

	public String setEditModeForUpdate(String issueId){

		issue = issueService.readIssue(Integer.valueOf(issueId));

		isEditMode = Boolean.TRUE;

		projectId = issue.getProject().getProjectId();

		if(issue.getAssignees() != null && issue.getAssignees().size() > 0){

			for(Assignee a: issue.getAssignees()){

				if(a.getRole().equals(Role.DEVELOPER)){
					this.developerId = a.getAccount().getAccountId();
				}

				if(a.getRole().equals(Role.ANALYST)){
					this.analystId = a.getAccount().getAccountId();
				}

				if(a.getRole().equals(Role.TESTER)){
					this.testerId = a.getAccount().getAccountId();
				}

				if(a.getRole().equals(Role.SYSADMIN)){
					this.sysAdminId = a.getAccount().getAccountId();
				}

				if(a.getRole().equals(Role.PM)){
					this.pMId = a.getAccount().getAccountId();
				}

				if(a.getRole().equals(Role.TRKADMIN)){
					this.trackerAdminId = a.getAccount().getAccountId();
				}

			}

		}

		return "issue";
	}

	public String submitCreate(){

		String page = "issueList";

		try{

			setAssignees();

			setDueDate();

			setProjectInIssue();

			issueService.createIssue(issue);

			refreshIssueList();

		}catch(TrackerException e){

			saveErrors(e);

			page = "issue";
		}

		clearAssignees();

		return page;
	}

	public String submitUpdate(){

		String page = "issueList";

		try{

			setProjectInIssue();

			setAssignees();

			issueService.updateIssue(issue);

			refreshIssueList();

		}catch(TrackerException e){

			saveErrors(e);

			page = "issue";
		}

		clearAssignees();

		return page;
	}

	public String submitDelete(){

		try{

			issueService.deleteIssue(issue);

			refreshIssueList();

		}catch(RuntimeException e){

			e.printStackTrace();
		}

		return "issueList";
	}

	public String submitCancel(){

		try{
			issue = null;
			isEditMode = Boolean.FALSE;

		}catch(RuntimeException e){
			e.printStackTrace();
		}

		return "issueList";
	}

	private void refreshIssueList(){

		IssueListView issueListView = (IssueListView) get("issueListView");

		issueListView.refreshIssueList();

	}

	private void setProjectInIssue(){

		issue.setProject(projectService.readProject(projectId));

	}

	private void setAssignees(){

		issue.setAssignees(null);

		if(analystId != null){

			Account analyst = accountService.readAccount(analystId);
			issue.addAssignee(analyst, Role.ANALYST);
		}

		if(developerId != null){

			Account developer = accountService.readAccount(developerId);
			issue.addAssignee(developer, Role.DEVELOPER);
		}

		if(testerId != null){

			Account tester = accountService.readAccount(testerId);
			issue.addAssignee(tester, Role.TESTER);
		}

		if(sysAdminId != null){

			Account sysAdmin = accountService.readAccount(sysAdminId);
			issue.addAssignee(sysAdmin, Role.SYSADMIN);
		}

		if(pMId != null){
			Account PM = accountService.readAccount(pMId);
			issue.addAssignee(PM, Role.PM);
		}

		if(trackerAdminId != null){
			Account trackerAdmin = accountService.readAccount(trackerAdminId);
			issue.addAssignee(trackerAdmin, Role.TRKADMIN);
		}

	}

	private void clearAssignees(){

		if(analystId != null){
			analystId = null;
		}

		if(developerId != null){
			developerId = null;

		}

		if(testerId != null){
			testerId = null;
		}

		if(sysAdminId != null){
			sysAdminId = null;
		}

		if(pMId != null){
			pMId = null;
		}

		if(trackerAdminId != null){
			trackerAdminId = null;
		}

	}

	private void setDueDate(){

		if(date != null){
			issue.setDue(date);
		}

	}

	public Issue getIssue(){

		return issue;
	}

	public void setIssue(Issue issue){

		this.issue = issue;
	}

	public List<Project> getProjects(){

		return projectService.listProjectsForAll(Boolean.TRUE);
	}

	public Integer getProjectId(){

		return projectId;
	}

	public void setProjectId(Integer projectId){

		this.projectId = projectId;
	}

	public Boolean getIsEditMode(){

		return isEditMode;
	}

	public Integer getDeveloperId(){

		return developerId;
	}

	public void setDeveloperId(Integer developerId){

		this.developerId = developerId;
	}

	public Integer getAnalystId(){

		return analystId;
	}

	public void setAnalystId(Integer analystId){

		this.analystId = analystId;
	}

	public Integer getTesterId(){

		return testerId;
	}

	public void setTesterId(Integer testerId){

		this.testerId = testerId;
	}

	public Integer getSysAdminId(){

		return sysAdminId;
	}

	public void setSysAdminId(Integer sysAdminId){

		this.sysAdminId = sysAdminId;
	}

	public Integer getpMId(){

		return pMId;
	}

	public void setpMId(Integer pMId){

		this.pMId = pMId;
	}

	public Integer getTrackerAdminId(){

		return trackerAdminId;
	}

	public void setTrackerAdminId(Integer trackerAdminId){

		this.trackerAdminId = trackerAdminId;
	}

	public Date getDate(){

		return date;
	}

	public void setDate(Date date){

		this.date = date;
	}

}
