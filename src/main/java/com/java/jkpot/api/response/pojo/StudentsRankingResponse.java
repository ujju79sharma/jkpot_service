package com.java.jkpot.api.response.pojo;

public class StudentsRankingResponse {

	private String userId;
	private String sectionalName;
	private String subSectionName;
	private String userName;
	private double totalMarks;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSectionalName() {
		return sectionalName;
	}

	public void setSectionalName(String sectionalName) {
		this.sectionalName = sectionalName;
	}

	public String getSubSectionName() {
		return subSectionName;
	}

	public void setSubSectionName(String subSectionName) {
		this.subSectionName = subSectionName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(double totalMarks) {
		this.totalMarks = totalMarks;
	}
}