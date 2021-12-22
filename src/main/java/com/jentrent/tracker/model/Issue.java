package com.jentrent.tracker.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jentrent.tracker.dao.PriorityConverter;
import com.jentrent.tracker.dao.StatusConverter;
import com.jentrent.tracker.dao.TypeConverter;

@Entity
public class Issue{

	@Id
	@Column(name = "ISSUE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer issueId;

	@NotNull(message = "Type cannot be empty")
	@Column(name = "TYPE")
	@Convert(converter = TypeConverter.class)
	private Type type;

	@Column(name = "STATUS", columnDefinition = "integer default 1")
	@Convert(converter = StatusConverter.class)
	private Status status;

	@Column(name = "PRIORITY", columnDefinition = "integer default 2")
	@Convert(converter = PriorityConverter.class)
	private Priority priority;

	@Size(min = 5, max = 50, message = "Title must be between 5 and 50 characters long")
	@Column(name = "TITLE")
	private String title;

	@Size(min = 5, message = "Description must be a minimum of 5 characters long")
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "DUE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date due;

	@NotNull(message = "Created date cannot be empty")
	@Column(name = "CREATED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@NotNull(message = "Modified date cannot be empty")
	@Column(name = "MODIFIED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	@ManyToOne
	@JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")
	private Project project;

	@NotNull(message = "Issue creator cannot be empty")
	@ManyToOne
	@JoinColumn(name = "CREATED_BY", referencedColumnName = "ACCOUNT_ID")
	private Account createdBy;

	@OneToMany(mappedBy = "issue")
	private List<Assignee> assignees;

	public Integer getIssueId(){

		return issueId;
	}

	public void setIssueId(Integer issueId){

		this.issueId = issueId;
	}

	public Type getType(){

		return type;
	}

	public void setType(Type type){

		this.type = type;
	}

	public Status getStatus(){

		return status;
	}

	public void setStatus(Status status){

		this.status = status;
	}

	public Priority getPriority(){

		return priority;
	}

	public void setPriority(Priority priority){

		this.priority = priority;
	}

	public String getTitle(){

		return title;
	}

	public void setTitle(String title){

		this.title = title;
	}

	public String getDescription(){

		return description;
	}

	public void setDescription(String description){

		this.description = description;
	}

	public Date getDue(){

		return due;
	}

	public void setDue(Date due){

		this.due = due;
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

	public Project getProject(){

		return project;
	}

	public void setProject(Project project){

		this.project = project;
	}

	public Account getCreatedBy(){

		return createdBy;
	}

	public void setCreatedBy(Account createdBy){

		this.createdBy = createdBy;
	}

	public List<Assignee> getAssignees(){

		return assignees;
	}

	public void setAssignees(List<Assignee> assignees){

		this.assignees = assignees;
	}

	public void addAssignee(Account account, Role role){

		if(assignees == null){
			assignees = new LinkedList<Assignee>();
		}

		Assignee assignee = new Assignee();
		assignee.setAccount(account);
		assignee.setIssue(this);
		assignee.setRole(role);
		Date d = new Date();
		assignee.setCreated(d);
		assignee.setModified(d);

		assignees.add(assignee);

	}

}
