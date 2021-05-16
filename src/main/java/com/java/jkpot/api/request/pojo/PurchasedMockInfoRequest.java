package com.java.jkpot.api.request.pojo;

public class PurchasedMockInfoRequest {

	private int purchasedMockId;
	private String userId;
	private int examId;
	private String description;
	private String subscriptionId;
	private String paymentId;

	public int getPurchasedMockId() {
		return purchasedMockId;
	}

	public void setPurchasedMockId(int purchasedMockId) {
		this.purchasedMockId = purchasedMockId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
}