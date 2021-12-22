package com.jentrent.tracker.dao;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.Assignee;
import com.jentrent.tracker.model.Project;

@Component("ProjectDAO")
public class ProjectDAOImpl extends BaseDAO implements ProjectDAO{

	public Project createProject(Project project){

		Date d = new Date();
		project.setCreated(d);
		project.setModified(d);

		beginTrx();
		getEm().persist(project);
		commitTrx();

		return project;
	}

	public Project readProject(Integer projectId){

		Query q = getEm().createQuery("select p from Project p where p.projectId = :projectId");
		q.setParameter("projectId", projectId.intValue());

		try{
			return (Project) q.getSingleResult();
		}catch(Exception ignored){
		}

		return null;
	}

	public Project readProject(String name){

		Query q = getEm().createQuery("select p from Project p where p.name = :name");
		q.setParameter("name", name);

		try{
			return (Project) q.getSingleResult();
		}catch(NoResultException ignored){
			return null;
		}

	}

	public Project updateProject(Project project){

		project.setModified(new Date());
		getEm().getTransaction().begin();
		getEm().merge(project);
		getEm().getTransaction().commit();
		return readProject(project.getProjectId());
	}

	public void deleteProject(Project project){

		beginTrx();

		if(getEm().contains(project)){
			getEm().remove(project);
		}else{
			getEm().remove(getEm().merge(project));
		}

		commitTrx();

	}

	public List<Project> listProjectsForAll(){

		getEm().clear();

		TypedQuery<Project> q = getEm().createQuery("select p from Project p order by name", Project.class);

		return q.getResultList();

	}

	public List<Project> listProjectsForAll(Boolean isOpen){

		getEm().clear();

		TypedQuery<Project> q = null;

		if(isOpen){
			q = getEm().createQuery("select p from Project p where isOpen = true order by name", Project.class);
		}else{
			q = getEm().createQuery("select p from Project p where isOpen = false order by name", Project.class);
		}

		return q.getResultList();
	}

	public List<Project> listProjectsForCreator(Account accountId){

		getEm().clear();

		TypedQuery<Project> q = getEm().createQuery("select p from Project p where p.createdBy = :createdBy order by name", Project.class);
		q.setParameter("createdBy", accountId);

		return q.getResultList();
	}

	public List<Project> listProjectsForAssignee(Account accountId){

		getEm().clear();

		TypedQuery<Assignee> q = getEm().createQuery("select a from Assignee a where a.account.accountId = :accountId", Assignee.class);
		q.setParameter("accountId", accountId.getAccountId().intValue());

		List<Assignee> assignes = q.getResultList();

		if(assignes != null && assignes.size() > 0){

			List<Project> projects = new LinkedList<Project>();

			for(Assignee a: assignes){

				if(!projects.contains(a.getIssue().getProject())){
					projects.add(a.getIssue().getProject());
				}

			}

			Collections.sort(projects);

			return projects;
		}else{
			return null;
		}

	}

}
