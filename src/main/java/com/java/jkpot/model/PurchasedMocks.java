package com.java.jkpot.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "purchased_mocks")
public class PurchasedMocks {

	@Id
	private int purchasedMockId;
	private String userId;
	private String subscriptionId;
	private String description;
	private LocalDate purchasedDate;
	private int examId;
	private String examName;
	private String status;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getPurchasedMockId() {
		return purchasedMockId;
	}

	public void setPurchasedMockId(int purchasedMockId) {
		this.purchasedMockId = purchasedMockId;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public LocalDate getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(LocalDate purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PurchasedMocks [purchasedMockId=" + purchasedMockId + ", userId=" + userId + ", subscriptionId="
				+ subscriptionId + ", purchasedDate=" + purchasedDate + ", examId=" + examId + "]";
	}
}