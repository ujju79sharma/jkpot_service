package com.java.jkpot.api.request.pojo;

import org.springframework.http.ResponseEntity;

import com.java.jkpot.api.response.pojo.RestResponse;

public class PurchasedMockInfoRequest {

	private int purchasedMockId;
	private String userId;
	private int examId;
	private String subscriptionId;

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

	public ResponseEntity<RestResponse> addPurchasedMockOfUser(PurchasedMockInfoRequest purchasedMockInfoRequest) {
		// TODO Auto-generated method stub
		return null;
	}
}