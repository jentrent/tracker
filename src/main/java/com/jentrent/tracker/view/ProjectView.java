package com.jentrent.tracker.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;

import com.jentrent.tracker.model.Project;
import com.jentrent.tracker.service.ProjectService;
import com.jentrent.tracker.service.TrackerException;

@ManagedBean
@SessionScoped
public class ProjectView extends BaseView implements Serializable{

	private Boolean isEditMode = Boolean.FALSE;
	private Project project;

	@Autowired
	private ProjectService projectService;

	@PostConstruct
	public void init(){

		super.init();

		if(getRequestParam("projectId") != null){

			String projectId = (String) getRequestParam("projectId");

			setEditModeForUpdate(projectId);

		}else if(get("projectId") != null){

			String projectId = (String) get("projectId");

			set("projectId", null);

			setEditModeForUpdate(projectId);

		}else{

			setEditModeForCreate();
		}

	}

	public String setEditModeForUpdate(String projectId){

		project = projectService.readProject(Integer.valueOf(projectId));

		isEditMode = Boolean.TRUE;

		return "project";
	}

	public String setEditModeForCreate(){

		project = new Project();

		project.setCreatedBy(getAccount());

		isEditMode = Boolean.FALSE;

		return "project";
	}

	public String submitCreate(){

		try{

			projectService.createProject(project);

		}catch(TrackerException e){

			saveErrors(e);

			return "project";
		}

		return "projectList";
	}

	public String submitUpdate(){

		try{

			projectService.updateProject(project);

		}catch(TrackerException e){

			saveErrors(e);

			return "project";
		}

		return "projectList";
	}

	public String submitDelete(){

		try{

			if(project.getIssues() != null && project.getIssues().size() > 0){

				saveError("Project cannot be deleted, as it has Issues.\nYou can only 'Close' the Project");

				set("projectId", project.getProjectId().toString());

				return "project";
			}

			projectService.deleteProject(project);

		}catch(RuntimeException e){
			e.printStackTrace();
		}

		return "projectList";
	}

	public String submitCancel(){

		try{
			project = null;
			isEditMode = Boolean.FALSE;

		}catch(RuntimeException e){
			e.printStackTrace();
		}

		return "projectList";
	}

	public Project getProject(){

		return project;
	}

	public void setProject(Project project){

		this.project = project;
	}

	public Boolean getIsEditMode(){

		return isEditMode;
	}

}
