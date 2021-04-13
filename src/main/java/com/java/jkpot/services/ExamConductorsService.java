package com.java.jkpot.services;

import org.springframework.http.ResponseEntity;

import com.java.jkpot.api.response.pojo.RestResponse;

public interface ExamConductorsService {

	ResponseEntity<RestResponse> createExamConductor (int examConductorId, String name, String logo);
	
	ResponseEntity<RestResponse> fetchExamConductor (int examConductorId);
	
	ResponseEntity<RestResponse> fetchExamConductors();
	
	ResponseEntity<RestResponse> deleteExamConductor(int examConductorId);
}
