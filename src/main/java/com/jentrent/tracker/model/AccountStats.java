package com.jentrent.tracker.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AccountStats{

	private Integer accountId;

	private Integer issueCount;

	private Integer projectCount;

	public Integer getAccountId(){

		return accountId;
	}

	public void setAccountId(Integer accountId){

		this.accountId = accountId;
	}

	public Integer getIssueCount(){

		return issueCount;
	}

	public void setIssueCount(Integer issueCount){

		this.issueCount = issueCount;
	}

	public Integer getProjectCount(){

		return projectCount;
	}

	public void setProjectCount(Integer projectCount){

		this.projectCount = projectCount;
	}

	public String toString(){

		return ToStringBuilder.reflectionToString(this);
	}

}
