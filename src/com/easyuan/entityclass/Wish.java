package com.easyuan.entityclass;

import java.io.Serializable;

public class Wish implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6390339486367272544L;
	private int id;
	private String content;
	private int status;
	private int ownerId;
	private int adopterId;
	private String createdAt;
	private String updatedAt;
	private int validTimeSpan;
	
	public int getId() {
		return id;
	}
	public void setId(int _id) {
		this.id = _id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int owner_id) {
		this.ownerId = owner_id;
	}
	public int getAdopterId() {
		return adopterId;
	}
	public void setAdopterId(int adopter_id) {
		this.adopterId = adopter_id;
	}
	public String getCreatedTime() {
		return createdAt;
	}
	public void setCreatedTime(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedTime() {
		return updatedAt;
	}
	public void setUpdatedTime(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public int getValidTimeSpan() {
		return validTimeSpan;
	}
	public void setValidTimeSpan(int valid_time_span) {
		this.validTimeSpan = valid_time_span;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
