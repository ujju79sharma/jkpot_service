package com.java.jkpot.services;

import org.springframework.http.ResponseEntity;

import com.java.jkpot.api.response.pojo.RestResponse;

public interface ExamPdfService {

	ResponseEntity<RestResponse> createExamPdf (int examPdfId, int examId, String pdfName, String pdfUrl);
	
	ResponseEntity<RestResponse> readExamPdf (int examPdfId);
	
	ResponseEntity<RestResponse> readAllExamPdfs (int examId);
	
	ResponseEntity<RestResponse> deleteExamPdf (int examPdfId);
}
