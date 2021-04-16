package com.java.jkpot.services;

import org.springframework.http.ResponseEntity;

import com.java.jkpot.api.response.pojo.RestResponse;

public interface ExamsService {

	ResponseEntity<RestResponse> createExam(int examConductorId, int examId, String examName, String examLogo);
	
	ResponseEntity<RestResponse> findExamById(int examId);
	
	ResponseEntity<RestResponse> findAllExams(int examConductorId);
	
	ResponseEntity<RestResponse> deleteExamData (int examId);
}
