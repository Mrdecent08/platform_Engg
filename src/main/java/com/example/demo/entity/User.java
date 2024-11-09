package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Users")
public class User {

	 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String userId;
    private String userName;
    private int experience;
 
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Projects> projectDetails;
 
    // Getters and Setters
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
    	this.id = id;
    }
 
    public String getUserId() {
        return userId;
    }
 
    public void setUserId(String userId) {
        this.userId = userId;
    }
 
    public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public int getExperience() {
        return experience;
    }
 
    public void setExperience(int experience) {
        this.experience = experience;
    }
 
    public List<Projects> getProjectDetails() {
        return projectDetails;
    }
 
    public void setProjectDetails(List<Projects> projectDetails) {
        this.projectDetails = projectDetails;
    }
	
}
