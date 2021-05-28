package com.java.jkpot.services;

import org.springframework.http.ResponseEntity;

import com.java.jkpot.api.request.pojo.StudentAnswersRequest;
import com.java.jkpot.api.request.pojo.UpdateSectionalMockRequest;
import com.java.jkpot.api.response.pojo.RestResponse;

public interface SectionalMockService {

	ResponseEntity<RestResponse> findSectionalMockBySectionalIdAndSubSectionalId(int examId, int sectionalId, int subSectionalId);

	ResponseEntity<RestResponse> updateSectionalMockBySectionalIdAndSubSectionalId(UpdateSectionalMockRequest sectionalMockUpdateObj);

	ResponseEntity<RestResponse> findStudentMarks(StudentAnswersRequest studentAnswersRequest);

	RestResponse findHighestMarksOfStudents(int examId, int sectionalId, int subSectionalId, String userId);

	ResponseEntity<RestResponse> fetchAttemptedMocksOfUser(String userId);

}
