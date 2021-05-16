package com.java.jkpot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.jkpot.api.request.pojo.PurchasedMockInfoRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.services.PurchasedMockService;

@RestController
@RequestMapping("/user")
public class PurchasedMocksController {

	@Autowired
	private PurchasedMockService purchasedMockService;
	
	@PostMapping(path = "/add/purchased/mock")
	public ResponseEntity<RestResponse> addPurchasedMockOfUser(@RequestBody PurchasedMockInfoRequest purchasedMockInfoRequest) {
		
		return purchasedMockService.addPurchasedMockOfUser(purchasedMockInfoRequest);
	}

	@GetMapping(path ="read/all/purchased/{userId}")
	public ResponseEntity<RestResponse> fetchPurchasedMockOfUser(@PathVariable(name = "userId") String userId) {
		
		return purchasedMockService.fetchPurchasedMockOfUser(userId);
	}

	@DeleteMapping(path = "/delete/purchased/mock")
	public ResponseEntity<RestResponse> deletePurchasedMockOfUser(@RequestBody PurchasedMockInfoRequest purchasedMockInfoRequest) {
		return purchasedMockService.deletePurchasedMockOfUser(purchasedMockInfoRequest);
	}
}