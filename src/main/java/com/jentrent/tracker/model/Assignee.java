package com.jentrent.tracker.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.jentrent.tracker.dao.RoleConverter;

@Entity
public class Assignee{

	@Id
	@Column(name = "ASSIGNEE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer assigneeId;

	@NotNull(message = "Assginee's role cannot be empty")
	@Column(name = "ROLE")
	@Convert(converter = RoleConverter.class)
	private Role role;

	@NotNull(message = "Created date cannot be empty")
	@Column(name = "CREATED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@NotNull(message = "Modified date cannot be empty")
	@Column(name = "MODIFIED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	@NotNull(message = "Issue cannot be empty")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ISSUE_ID", referencedColumnName = "ISSUE_ID")
	private Issue issue;

	@NotNull(message = "Assignee AccountView cannot be empty")
	@ManyToOne
	@JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID")
	private Account account;

	public Integer getAssigneeId(){

		return assigneeId;
	}

	public void setAssigneeId(Integer assigneeId){

		this.assigneeId = assigneeId;
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

	public Issue getIssue(){

		return issue;
	}

	public void setIssue(Issue issue){

		this.issue = issue;
	}

	public Account getAccount(){

		return account;
	}

	public void setAccount(Account account){

		this.account = account;
	}

	public String getFullName(){

		return account.getLastName() + ", " + account.getFirstName();
	}

	public String toString(){

		return ToStringBuilder.reflectionToString(this);
	}

}
