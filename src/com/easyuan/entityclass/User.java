package com.easyuan.entityclass;

import java.io.Serializable;

public class User implements Serializable {
	/** serialVersionUID*/
	private static final long serialVersionUID = -5679754213266158649L;
	private int userId;
	private String nickname;
	private String password;
	private String email;
	private boolean emailIsVerified;
	private int campusId;
	private boolean gender;
	private String avatar;
	private String score;
	private int wishChanceCount;

	public int getId() {
		return userId;
	}

	public void setId(int id) {
		this.userId = id;
	}

	public String getNickName() {
		return nickname;
	}

	public void setNickName(String nickName) {
		this.nickname = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEmailVerified() {
		return emailIsVerified;
	}

	public void setEmailVerified(boolean isEmailVerified) {
		this.emailIsVerified = isEmailVerified;
	}

	public int getCampusId() {
		return campusId;
	}

	public void setCampusId(int campusId) {
		this.campusId = campusId;
	}

	public boolean getGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public int getWishChanceCount() {
		return wishChanceCount;
	}

	public void setWishChanceCount(int wishChangeCount) {
		this.wishChanceCount = wishChangeCount;
	}

}
