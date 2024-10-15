package com.example.demo.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class awsEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	private String volumeId;
	
	private String machineId;
	
	private String machineIp;
	
	private String storageAllocated;
	
	private String storageUtilized;
	
	private String utilizationPercentage;
	
	private String recommendation;

	public awsEntity() {
		super();
	}

	public awsEntity(String volumeId, String machineId, String machineIp, String storageAllocated,
			String storageUtilized, String utilizationPercentage, String recommendation) {
		super();
		this.volumeId = volumeId;
		this.machineId = machineId;
		this.machineIp = machineIp;
		this.storageAllocated = storageAllocated;
		this.storageUtilized = storageUtilized;
		this.utilizationPercentage = utilizationPercentage;
		this.recommendation = recommendation;
	}

	public awsEntity(int id, String volumeId, String machineId, String machineIp, String storageAllocated,
			String storageUtilized, String utilizationPercentage, String recommendation) {
		super();
		this.id = id;
		this.volumeId = volumeId;
		this.machineId = machineId;
		this.machineIp = machineIp;
		this.storageAllocated = storageAllocated;
		this.storageUtilized = storageUtilized;
		this.utilizationPercentage = utilizationPercentage;
		this.recommendation = recommendation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVolumeId() {
		return volumeId;
	}

	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getMachineIp() {
		return machineIp;
	}

	public void setMachineIp(String machineIp) {
		this.machineIp = machineIp;
	}

	public String getStorageAllocated() {
		return storageAllocated;
	}

	public void setStorageAllocated(String storageAllocated) {
		this.storageAllocated = storageAllocated;
	}

	public String getStorageUtilized() {
		return storageUtilized;
	}

	public void setStorageUtilized(String storageUtilized) {
		this.storageUtilized = storageUtilized;
	}

	public String getUtilizationPercentage() {
		return utilizationPercentage;
	}

	public void setUtilizationPercentage(String utilizationPercentage) {
		this.utilizationPercentage = utilizationPercentage;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	@Override
	public String toString() {
		return "awsEntity [id=" + id + ", volumeId=" + volumeId + ", machineId=" + machineId + ", machineIp="
				+ machineIp + ", storageAllocated=" + storageAllocated + ", storageUtilized=" + storageUtilized
				+ ", utilizationPercentage=" + utilizationPercentage + ", recommendation=" + recommendation + "]";
	}

	
	
	
}
