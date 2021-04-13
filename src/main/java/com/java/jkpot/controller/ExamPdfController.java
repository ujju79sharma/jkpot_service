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

import com.java.jkpot.api.request.pojo.ExamPdfDetailsRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.services.ExamPdfService;

@RestController
@RequestMapping("/exampdf")
public class ExamPdfController {

	@Autowired
	private ExamPdfService examPdfService;

	@PostMapping(path = "/create")
	public ResponseEntity<RestResponse> createExamPdf(@RequestBody ExamPdfDetailsRequest examPdfDetailsRequest) {
		return examPdfService.createExamPdf(examPdfDetailsRequest.getExamPdfId(), examPdfDetailsRequest.getExamId(), 
				examPdfDetailsRequest.getPdfName(), examPdfDetailsRequest.getPdfUrl());
	}
	
	@GetMapping(path = "/read/{examPdfId}")
	public ResponseEntity<RestResponse> readExamPfd(@PathVariable(value = "examPdfId") int examPdfId) {
		return examPdfService.readExamPdf(examPdfId);
	}
	
	@GetMapping(path = "/read/all/{examId}")
	public ResponseEntity<RestResponse> readAllExamPfds(@PathVariable(value = "examId") int examId) {
		return examPdfService.readAllExamPdfs(examId);
	}
	
	@DeleteMapping(path = "/delete/{examPdfId}")
	public ResponseEntity<RestResponse> deleteExamPfd(@PathVariable(value = "examPdfId") int examPdfId) {
		return examPdfService.deleteExamPdf(examPdfId);
	}
}
