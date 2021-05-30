package com.java.jkpot.services;

import org.springframework.http.ResponseEntity;

import com.java.jkpot.api.request.pojo.StudentAnswersRequest;
import com.java.jkpot.api.response.pojo.RestResponse;

public interface FullLengthMocksService {

	ResponseEntity<RestResponse> fetchSectionInfoByExamId(int examId);
	
	ResponseEntity<RestResponse> findSectionalMockByExamIdAndMockId(int examId, int mockId);

	ResponseEntity<RestResponse> uploadAndFindStudentMarks(StudentAnswersRequest studentAnswersRequest);

	RestResponse fetchTopStudentsInAMock(int examId, int mockId, String userId);

	ResponseEntity<RestResponse> fetchTopStudentsInAllMocks();

}
