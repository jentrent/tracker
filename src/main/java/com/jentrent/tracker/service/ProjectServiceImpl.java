package com.jentrent.tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jentrent.tracker.dao.ProjectDAO;
import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.Project;

@Component("ProjectService")
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	private ProjectDAO projectDAO;

	public Project createProject(Project project) throws TrackerException{

		checkForDuplicateProject(project);
		return projectDAO.createProject(project);
	}

	public Project readProject(Integer projectId){

		return projectDAO.readProject(projectId);
	}

	public Project readProject(String name){

		return projectDAO.readProject(name);
	}

	public Project updateProject(Project project) throws TrackerException{

		checkForDuplicateProject(project);
		return projectDAO.updateProject(project);

	}

	public void deleteProject(Project project){

		projectDAO.deleteProject(project);
	}

	public List<Project> listProjectsForAll(){

		return projectDAO.listProjectsForAll();
	}

	public List<Project> listProjectsForAll(Boolean isOpen){

		return projectDAO.listProjectsForAll(isOpen);
	}

	public List<Project> listProjectsForCreator(Account accountId){

		return projectDAO.listProjectsForCreator(accountId);
	}

	public List<Project> listProjectsForAssignee(Account accountId){

		return projectDAO.listProjectsForAssignee(accountId);
	}

	private void checkForDuplicateProject(Project project) throws TrackerException{

		Project check = projectDAO.readProject(project.getName());

		if(check != null){

			if(check.getProjectId() != project.getProjectId()){

				throw new TrackerException(
						"The following project name is already " + "associated with an existing project: " + project.getName());
			}

		}

	}

	public void setProjectDAO(ProjectDAO projectDAO){

		this.projectDAO = projectDAO;
	}

}
