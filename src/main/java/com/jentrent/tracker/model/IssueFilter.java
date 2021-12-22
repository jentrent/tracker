package com.jentrent.tracker.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class IssueFilter implements Serializable{

	private Type type;

	private Status status;

	private Priority priority;

	private String text;

	private Date due;

	private Integer projectId;

	private Integer createdByAccountId;

	private Integer assigneeAccountId;

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

	public String getText(){

		return text;
	}

	public void setText(String text){

		this.text = text;
	}

	public Date getDue(){

		return due;
	}

	public void setDue(Date due){

		this.due = due;
	}

	public Integer getProjectId(){

		return projectId;
	}

	public void setProjectId(Integer projectId){

		this.projectId = projectId;
	}

	public Integer getCreatedByAccountId(){

		return createdByAccountId;
	}

	public void setCreatedByAccountId(Integer createdByAccountId){

		this.createdByAccountId = createdByAccountId;
	}

	public Integer getAssigneeAccountId(){

		return assigneeAccountId;
	}

	public void setAssigneeAccountId(Integer assigneeAccountId){

		this.assigneeAccountId = assigneeAccountId;
	}

	public String toString(){

		return ToStringBuilder.reflectionToString(this);
	}

}
