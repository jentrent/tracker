package com.jentrent.tracker.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.Project;
import com.jentrent.tracker.service.TrackerException;

public class ProjectServiceTest extends BaseTest{

	@Test
	public void testCreateProjectWithValidData() throws TrackerException{

		Account account = createAccount();
		assertNotNull(createProject(account));
	}

	@Test
	public void testReadProject() throws TrackerException{

		Account account = createAccount();
		Project project = createProject(account);

		Project check = getProjectService().readProject(project.getProjectId());
		assertNotNull(check);
	}

	@Test
	public void testUpdateProject() throws TrackerException{

		Account account = createAccount();
		Project project = createProject(account);

		String newText = "Updated Project" + System.currentTimeMillis();
		project.setDescription(newText);
		project.setIsOpen(true);
		Project check = getProjectService().updateProject(project);
		assertTrue(check.getDescription().equals(newText));
		assertTrue(check.getIsOpen().equals(true));
	}

	public void testDeleteProject() throws TrackerException{

		Account account = createAccount();
		Project project = createProject(account);

		getProjectService().deleteProject(project);
		Project check = getProjectService().readProject(project.getProjectId());

		assertNull(check);
	}

	@Test
	public void testListProjectsForAll() throws TrackerException{

		Account account = createAccount();

		List<Project> list = new ArrayList<Project>();
		Project Project1 = createProject(account);
		Project Project2 = createProject(account);
		Project Project3 = createProject(account);
		list.add(Project1);
		list.add(Project2);
		list.add(Project3);

		List<Project> check = getProjectService().listProjectsForCreator(account);

		assertTrue(list.size() == check.size());

		for(Project project: list){

			boolean found = false;

			for(Project c: check){

				if(project.getCreatedBy().getAccountId().equals(c.getCreatedBy().getAccountId())){
					found = true;
				}

			}

			assertTrue(found);
		}

	}

	public static void main(String[] args){

		ProjectServiceTest pst = new ProjectServiceTest();

		try{
			pst.setUp();
			pst.testListProjectsForAll();
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
