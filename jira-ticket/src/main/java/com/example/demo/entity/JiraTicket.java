package com.example.demo.entity;

public class JiraTicket {
	private String projectKey;
	private String summary;
	private String description;
	private String issueType;
	public JiraTicket() {
		super();
	}
	public JiraTicket(String projectKey, String summary, String description, String issueType) {
		super();
		this.projectKey = projectKey;
		this.summary = summary;
		this.description = description;
		this.issueType = issueType;
	}
	public String getProjectKey() {
		return projectKey;
	}
	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIssueType() {
		return issueType;
	}
	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}
	@Override
	public String toString() {
		return "JiraTicket [projectKey=" + projectKey + ", summary=" + summary + ", description=" + description
				+ ", issueType=" + issueType + "]";
	}
	
	
}
