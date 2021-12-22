package com.jentrent.tracker.service;

import java.util.List;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.Project;

public interface ProjectService{

	public Project createProject(Project project) throws TrackerException;

	public Project readProject(Integer projectId);

	public Project readProject(String name);

	public Project updateProject(Project project) throws TrackerException;

	public void deleteProject(Project project);

	public List<Project> listProjectsForAll();

	public List<Project> listProjectsForAll(Boolean isOpen);

	public List<Project> listProjectsForCreator(Account accountId);

	public List<Project> listProjectsForAssignee(Account accountId);

}
