package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Projects")
public class Projects {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String projectId;
	
	private String projectName;
	
	private int projectDuration;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Projects() {
		super();
	}

	public Projects(String projectId, String projectName, int projectDuration, User user) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectDuration = projectDuration;
		this.user = user;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getProjectDuration() {
		return projectDuration;
	}

	public void setProjectDuration(int projectDuration) {
		this.projectDuration = projectDuration;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Projects [projectId=" + projectId + ", projectName=" + projectName + ", projectDuration="
				+ projectDuration + ", user=" + user + "]";
	}

	
}
