package com.java.jkpot.api.request.pojo;

public class ExamsStoreDeailsRequest {

	private int examStoreId;
	private int examId;
	private int examConductorId;
	private String examLogo;
	private double price;

	public int getExamStoreId() {
		return examStoreId;
	}

	public void setExamStoreId(int examStoreId) {
		this.examStoreId = examStoreId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getExamConductorId() {
		return examConductorId;
	}

	public void setExamConductorId(int examConductorId) {
		this.examConductorId = examConductorId;
	}

	public String getExamLogo() {
		return examLogo;
	}

	public void setExamLogo(String examLogo) {
		this.examLogo = examLogo;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
