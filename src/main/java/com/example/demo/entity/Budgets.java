package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Budgets {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String projectName;
	
	private double tokenLimit;
	
	private int budget;
	
	private double remainingTokens;

	public Budgets() {
		super();
	}

	public Budgets(String projectName, double tokenLimit, int budget, double remainingTokens) {
		super();
		this.projectName = projectName;
		this.tokenLimit = tokenLimit;
		this.budget = budget;
		this.remainingTokens = remainingTokens;
	}

	public Budgets(int id, String projectName, double tokenLimit, int budget, double remainingTokens) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.tokenLimit = tokenLimit;
		this.budget = budget;
		this.remainingTokens = remainingTokens;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public double getTokenLimit() {
		return tokenLimit;
	}

	public void setTokenLimit(double tokenLimit) {
		this.tokenLimit = tokenLimit;
	}

	public int getbudget() {
		return budget;
	}

	public void setbudget(int budget) {
		this.budget = budget;
	}

	public double getRemainingTokens() {
		return remainingTokens;
	}

	public void setRemainingTokens(double remainingTokens) {
		this.remainingTokens = remainingTokens;
	}

	@Override
	public String toString() {
		return "Budgets [id=" + id + ", projectName=" + projectName + ", tokenLimit=" + tokenLimit + ", budget=" + budget
				+ ", remainingTokens=" + remainingTokens + "]";
	}
	
	
}
