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
	
	private String machineName;
	
	private String machineIp;
	
	private String volume;
	
	private String occupiedPercentage;
	
	private String recommendation;

	public awsEntity() {
		super();
	}

	public awsEntity(String recommendation,String volumeId, String machineName, String machineIp, String volume, String occupiedPercentage) {
		super();
		this.volumeId = volumeId;
		this.machineName = machineName;
		this.machineIp = machineIp;
		this.volume = volume;
		this.occupiedPercentage = occupiedPercentage;
		this.recommendation = recommendation;
	}

	public awsEntity(int id, String volumeId, String machineName, String machineIp, String volume,
			String occupiedPercentage,String recommendation) {
		super();
		this.id = id;
		this.volumeId = volumeId;
		this.machineName = machineName;
		this.machineIp = machineIp;
		this.volume = volume;
		this.occupiedPercentage = occupiedPercentage;
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

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getMachineIp() {
		return machineIp;
	}

	public void setMachineIp(String machineIp) {
		this.machineIp = machineIp;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getOccupiedPercentage() {
		return occupiedPercentage;
	}

	public void setOccupiedPercentage(String occupiedPercentage) {
		this.occupiedPercentage = occupiedPercentage;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	@Override
	public String toString() {
		return "awsEntity [id=" + id + ", volumeId=" + volumeId + ", machineName=" + machineName + ", machineIp="
				+ machineIp + ", volume=" + volume + ", occupiedPercentage=" + occupiedPercentage + ", recommendation="
				+ recommendation + "]";
	}

	

	
}
