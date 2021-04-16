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

import com.java.jkpot.api.request.pojo.ExamDetailsRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.services.ExamsService;

@RestController
@RequestMapping("exams")
public class ExamsController {

	@Autowired
	private ExamsService examService;
	
	@PostMapping("/create")
	public ResponseEntity<RestResponse> createNewExam(@RequestBody ExamDetailsRequest examDetailsRequest) {
		
		return examService.createExam(examDetailsRequest.getExamConductorId(), examDetailsRequest.getExamId(), 
				examDetailsRequest.getExamName(), examDetailsRequest.getExamLogo());
	}
	
	@GetMapping("/read/{examId}")
	public ResponseEntity<RestResponse> findAnExam(@PathVariable(value = "examId") int examId) {
		
		return examService.findExamById(examId);
	}
	
	@GetMapping("/findAllExams/{examConductorId}")
	public ResponseEntity<RestResponse> findAllExam(@PathVariable(value = "examConductorId") int examConductorId) {

		return examService.findAllExams();
	}
	
	@DeleteMapping("/delete/{examId}")
	public ResponseEntity<RestResponse> deleteAnExam(@PathVariable(value = "examId") int examId) {
		
		return examService.deleteExamData(examId);
	}
}
