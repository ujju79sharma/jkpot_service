package com.java.jkpot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.services.ExamSyllabusService;

@RestController
@RequestMapping("/exams")
public class ExamSyllabusController {

	@Autowired
	private ExamSyllabusService examSyllabusService;
	
	@GetMapping("/syllabus/read/{examId}")
	public ResponseEntity<RestResponse> getExamSyllabusByExamId(@PathVariable(value = "examId") int examId) {
		
		return examSyllabusService.getExamSyllabusByExamId(examId);
	}
}
