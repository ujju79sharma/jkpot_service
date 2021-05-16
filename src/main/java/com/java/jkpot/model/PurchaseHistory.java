package com.java.jkpot.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "purchase_history")
public class PurchaseHistory {

	@Id
	private int purchaseHistoryId;
	private String userId;
	private String paymentId;
	private String subscriptionId;
	private int examId;
	private LocalDate purchasedDate;
	private String status;

	public int getPurchaseHistoryId() {
		return purchaseHistoryId;
	}

	public void setPurchaseHistoryId(int purchaseHistoryId) {
		this.purchaseHistoryId = purchaseHistoryId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public LocalDate getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(LocalDate purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PurchaseHistory [purchaseHistoryId=" + purchaseHistoryId + ", userId=" + userId + ", paymentId="
				+ paymentId + ", subscriptionId=" + subscriptionId + ", examId=" + examId + "]";
	}
}