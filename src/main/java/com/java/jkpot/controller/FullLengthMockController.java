package com.java.jkpot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.jkpot.api.request.pojo.StudentAnswersRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.services.FullLengthMocksService;

@RestController
@RequestMapping("/fulllengthmock")
public class FullLengthMockController {

	@Autowired
	private FullLengthMocksService fullLengthMockService;

	@GetMapping(path = "/find/sectioninfo/{examId}")
	public ResponseEntity<RestResponse> findSectionInfoByExamId(@PathVariable(value = "examId") int examId) {
		
		return fullLengthMockService.fetchSectionInfoByExamId(examId);
	}
	
	@GetMapping(path = "/find/{examId}/{mockId}")
	public ResponseEntity<RestResponse> findSectionalMockByExamIdAndMockId(@PathVariable(value = "examId") int examId,
			@PathVariable(value = "mockId") int mockId) {
		
		return fullLengthMockService.findSectionalMockByExamIdAndMockId(examId, mockId);
	}
	
	@PostMapping(path = "/upload/student/answers")
	public ResponseEntity<RestResponse> uploadAndFindStudentMarks(@RequestBody StudentAnswersRequest studentAnswersRequest) {

		return fullLengthMockService.uploadAndFindStudentMarks(studentAnswersRequest);
	}
	
	@GetMapping(path = "/find/topstudents/{examId}/{mockId}/{userId}")
	public ResponseEntity<RestResponse> findFullLengthMockTopStudentsExamIdAndMockId(@PathVariable(value = "examId") int examId,
			@PathVariable(value = "mockId") int mockId, 
			@PathVariable(value = "userId") String userId) {
		
		return fullLengthMockService.fetchTopStudentsInAMock(examId, mockId, userId);
	}
}
