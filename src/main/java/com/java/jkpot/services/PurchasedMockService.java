package com.java.jkpot.services;

import org.springframework.http.ResponseEntity;

import com.java.jkpot.api.request.pojo.PurchasedMockInfoRequest;
import com.java.jkpot.api.response.pojo.RestResponse;

public interface PurchasedMockService {

	ResponseEntity<RestResponse> addPurchasedMockOfUser(PurchasedMockInfoRequest purchasedMockInfoRequest);

	ResponseEntity<RestResponse> deletePurchasedMockOfUser(PurchasedMockInfoRequest purchasedMockInfoRequest);

	ResponseEntity<RestResponse> fetchPurchasedMockOfUser(String userId);

}