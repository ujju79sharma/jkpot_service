package com.java.jkpot.services;

import org.springframework.http.ResponseEntity;

import com.java.jkpot.api.response.pojo.RestResponse;

public interface ExamSyllabusService {

	ResponseEntity<RestResponse> getExamSyllabusByExamId(int examId);
}
