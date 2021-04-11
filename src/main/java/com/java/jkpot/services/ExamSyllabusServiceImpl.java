package com.java.jkpot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.model.ExamSyllabus;
import com.java.jkpot.repositories.ExamSyllabusRepository;

@Service
public class ExamSyllabusServiceImpl implements ExamSyllabusService {

	@Autowired
	private ExamSyllabusRepository examSyllabusRepository;
	
	@Override
	public ResponseEntity<RestResponse> getExamSyllabusByExamId(int examId) {
		
		List<ExamSyllabus> examSyllabusList = examSyllabusRepository.findByExamId(examId);
		
		if (examSyllabusList != null && examSyllabusList.size() > 0) {
			
			RestResponse response = new RestResponse("SUCCESS", examSyllabusList, 200);
			
			return ResponseEntity.ok(response);
		}else {
			RestResponse response = new RestResponse("FAILURE", examSyllabusList, 204);
			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}
	}

}
