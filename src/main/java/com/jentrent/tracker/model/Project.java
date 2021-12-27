package com.jentrent.tracker.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Project implements Comparable<Project>{

	@Id
	@Column(name = "PROJECT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer projectId;

	@Size(min = 5, max = 50, message = "Name must be between 5 and 50 characters long")
	@Column(name = "NAME")
	private String name;

	@Size(min = 5, message = "Description must be a minimum of 5 characters long")
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "IS_OPEN", columnDefinition = "boolean default true")
	private Boolean isOpen = Boolean.TRUE;

	@NotNull(message = "Created date cannot be empty")
	@Column(name = "CREATED")
	private Date created;

	@NotNull(message = "Modified date cannot be empty")
	@Column(name = "MODIFIED")
	private Date modified;

	@NotNull(message = "Project creator cannot be empty")
	@OneToOne
	@JoinColumn(name = "CREATED_BY", referencedColumnName = "ACCOUNT_ID")
	private Account createdBy;

	@OneToMany(mappedBy = "project")
	private List<Issue> issues;

	public Integer getProjectId(){

		return projectId;
	}

	public void setProjectId(Integer projectId){

		this.projectId = projectId;
	}

	public String getName(){

		return name;
	}

	public void setName(String name){

		this.name = name;
	}

	public String getDescription(){

		return description;
	}

	public void setDescription(String description){

		this.description = description;
	}

	public Boolean getIsOpen(){

		return isOpen;
	}

	public void setIsOpen(Boolean isOpen){

		if(isOpen != null){
			this.isOpen = isOpen;
		}

	}

	public Account getCreatedBy(){

		return createdBy;
	}

	public void setCreatedBy(Account createdBy){

		this.createdBy = createdBy;
	}

	public List<Issue> getIssues(){

		return issues;
	}

	public void setIssues(List<Issue> issues){

		this.issues = issues;
	}

	public Date getCreated(){

		return created;
	}

	public void setCreated(Date created){

		this.created = created;
	}

	public Date getModified(){

		return modified;
	}

	public void setModified(Date modified){

		this.modified = modified;
	}

	public String toString(){

		return name;
	}

	public int compareTo(Project p){

		return name.compareTo(p.name);
	}

}
