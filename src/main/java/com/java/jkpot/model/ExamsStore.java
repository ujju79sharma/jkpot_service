package com.java.jkpot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exams_store")
public class ExamsStore {

	@Id
	private int examStoreId;
	private int examId;
	private String examName;
	private int examConductorId;
	private String examConductorName;
	private String examLogo;
	private double price;
	private String subscriptionId;

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

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public int getExamConductorId() {
		return examConductorId;
	}

	public void setExamConductorId(int examConductorId) {
		this.examConductorId = examConductorId;
	}

	public String getExamConductorName() {
		return examConductorName;
	}

	public void setExamConductorName(String examConductorName) {
		this.examConductorName = examConductorName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getExamLogo() {
		return examLogo;
	}

	public void setExamLogo(String examLogo) {
		this.examLogo = examLogo;
	}

	@Override
	public String toString() {
		return "PurchaseExams [purchaseExamId=" + examStoreId + ", examId=" + examId + ", examName=" + examName
				+ ", examConductorId=" + examConductorId + ", examConductorName=" + examConductorName + ", price="
				+ price + ", subscriptionId=" + subscriptionId + "]";
	}
}