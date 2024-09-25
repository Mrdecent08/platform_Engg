package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class reportEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	private String bucketname;
	
	private String lifecycle;
	
	private String deletion;
	
	private String owner;

	public reportEntity() {
		super();
	}

	public reportEntity(String bucketname, String lifecycle, String deletion, String owner) {
		super();
		this.bucketname = bucketname;
		this.lifecycle = lifecycle;
		this.deletion = deletion;
		this.owner = owner;
	}

	public reportEntity(int id, String bucketname, String lifecycle, String deletion, String owner) {
		super();
		this.id = id;
		this.bucketname = bucketname;
		this.lifecycle = lifecycle;
		this.deletion = deletion;
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBucketname() {
		return bucketname;
	}

	public void setBucketname(String bucketname) {
		this.bucketname = bucketname;
	}

	public String getLifecycle() {
		return lifecycle;
	}

	public void setLifecycle(String lifecycle) {
		this.lifecycle = lifecycle;
	}

	public String getDeletion() {
		return deletion;
	}

	public void setDeletion(String deletion) {
		this.deletion = deletion;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "reportEntity [id=" + id + ", bucketname=" + bucketname + ", lifecycle=" + lifecycle + ", deletion="
				+ deletion + ", owner=" + owner + "]";
	}
	
	

}
