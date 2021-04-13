package com.java.jkpot.api.request.pojo;

public class ExamControllerDetailsRequest {

	private int examControllerId;
	private String name;
	private String logo;

	public int getExamControllerId() {
		return examControllerId;
	}

	public void setExamControllerId(int examControllerId) {
		this.examControllerId = examControllerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
}
