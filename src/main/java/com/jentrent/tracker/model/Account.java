package com.jentrent.tracker.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.jentrent.tracker.dao.RoleConverter;

@Entity
public class Account{

	@Id
	@Column(name = "ACCOUNT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer accountId;

	@Size(min = 1, max = 50, message = "First Name must be between 1 and 50 characters long")
	@Column(name = "FIRST_NAME")
	private String firstName;

	@Size(min = 1, max = 50, message = "Last Name must be between 1 and 50 characters long")
	@Column(name = "LAST_NAME")
	private String lastName;

	@Size(min = 7, max = 50, message = "Email must be between 1 and 50 characters long")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$", message = "Email is invalid")
	@Column(name = "EMAIL")
	private String email;

	@Size(min = 8, max = 24, message = "Password must be between 8 and 24 characters long")
	@Column(name = "PW_HASH")
	private String password;

	@NotNull(message = "Role cannot be empty")
	@Column(name = "ROLE")
	@Convert(converter = RoleConverter.class)
	private Role role;

	@NotNull(message = "Created date cannot be empty")
	@Column(name = "CREATED")
	private Date created;

	@NotNull(message = "Modified date cannot be empty")
	@Column(name = "MODIFIED")
	private Date modified;

	@OneToMany(mappedBy = "createdBy")
	private List<Project> projects;

	@OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Issue> issues;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Assignee> assignedTo;

	public Integer getAccountId(){

		return accountId;
	}

	public void setAccountId(Integer accountId){

		this.accountId = accountId;
	}

	public String getFirstName(){

		return firstName;
	}

	public void setFirstName(String firstName){

		this.firstName = firstName;
	}

	public String getLastName(){

		return lastName;
	}

	public void setLastName(String lastName){

		this.lastName = lastName;
	}

	public String getEmail(){

		return email;
	}

	public void setEmail(String email){

		this.email = email;
	}

	public String getPassword(){

		return password;
	}

	public void setPassword(String password){

		this.password = password;
	}

	public Role getRole(){

		return role;
	}

	public void setRole(Role role){

		this.role = role;
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

	public List<Project> getProjects(){

		return projects;
	}

	public void setProjects(List<Project> projects){

		this.projects = projects;
	}

	public List<Issue> getIssues(){

		return issues;
	}

	public void setIssues(List<Issue> issues){

		this.issues = issues;
	}

	public List<Assignee> getAssignedTo(){

		return assignedTo;
	}

	public void setAssignedTo(List<Assignee> assignedTo){

		this.assignedTo = assignedTo;
	}

	public String toString(){

		return ToStringBuilder.reflectionToString(this);
	}

}
