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

import com.java.jkpot.api.request.pojo.ExamsStoreDeailsRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.services.ExamsStoreService;

@RestController
@RequestMapping("/examstore")
public class ExamsStoreController {

	@Autowired
	private ExamsStoreService examStoreService;
	
	@PostMapping(path = "/create/mockexam")
	public ResponseEntity<RestResponse> createExamMocksInExamStore(@RequestBody ExamsStoreDeailsRequest examStoreDetailsRequest) {
		
		return examStoreService.createExamMocksInExamStore(examStoreDetailsRequest);
	}

	@GetMapping(path = "/read/all")
	public ResponseEntity<RestResponse> findAllExamsMocksInExamStore() {
		
		return examStoreService.findAllExamsMocksInExamStore();
	}

	@GetMapping(path = "/read/{examStoreId}")
	public ResponseEntity<RestResponse> findExamsMockInExamStore(@PathVariable(value = "examStoreId") int examStoreId) {

		return examStoreService.findExamMockInExamStore(examStoreId);
	}

	@DeleteMapping(path = "/delete/{examStoreId}")
	public ResponseEntity<RestResponse> deleteExamMockInExamStore(@PathVariable(value = "examStoreId") int examStoreId) {
		
		return examStoreService.deleteExamsMocksInExamStore(examStoreId);
	}
}