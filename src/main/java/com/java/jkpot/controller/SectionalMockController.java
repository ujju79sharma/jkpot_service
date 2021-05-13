package com.java.jkpot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.jkpot.api.request.pojo.StudentAnswersRequest;
import com.java.jkpot.api.request.pojo.UpdateSectionalMockRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.services.SectionalMockService;

@RestController
@RequestMapping("/sectionalMock")
public class SectionalMockController {

	@Autowired
	private SectionalMockService sectionalMockService;

	@GetMapping(value = "/find/{examId}/{sectionalId}/{subSectionalId}")
	public ResponseEntity<RestResponse> findSectionalMockBySectionalIdAndSubSectionalId(
			@PathVariable(value = "examId") int examId,
			@PathVariable(value = "sectionalId") int sectionalId, 
			@PathVariable(value = "subSectionalId") int subSectionalId) {

		return sectionalMockService.findSectionalMockBySectionalIdAndSubSectionalId(examId, sectionalId, subSectionalId);
	}

	@GetMapping(value = "/find/{examId}/{sectionalId}")
	public ResponseEntity<RestResponse> findSectionalMockBySectionalId (@PathVariable(value = "examId") int examId,
			@PathVariable(value = "sectionalId") int sectionalId) {

		return sectionalMockService.findSectionalMockBySectionalId(examId, sectionalId);
	}

	@PutMapping(value = "/update/fields")
	public ResponseEntity<RestResponse> updateSectionalMockQuestionBySectionalIdAndSubSectionalId(@RequestBody UpdateSectionalMockRequest sectionalMockUpdateObj) {

		return sectionalMockService.updateSectionalMockBySectionalIdAndSubSectionalId(sectionalMockUpdateObj);
	}

	@PostMapping(value = "student/answers")
	public ResponseEntity<RestResponse> findStudentsMarks(@RequestBody StudentAnswersRequest studentAnswersRequest) {

		return sectionalMockService.findStudentMarks(studentAnswersRequest);
	}

	@GetMapping(value = "/fetch/top/students/{examId}/{sectionId}/{subSectionId}/{userId}")
	public ResponseEntity<RestResponse> findHighestMarksOfStudents(@PathVariable(value = "examId") int examId,
			@PathVariable(value = "sectionId") int sectionalId, 
			@PathVariable(value = "subSectionId") int subSectionalId, 
			@PathVariable(value = "userId") String userId) {

		return sectionalMockService.findHighestMarksOfStudents(examId, sectionalId, subSectionalId, userId);
	}
	
	@GetMapping(value = "/fetch/ranking/allmocks/{userId}")
	public ResponseEntity<RestResponse> findHighestMarksOfStudentsInAllMocks(@PathVariable(value = "userId") String userId) {

		return sectionalMockService.findHighestMarksOfStudentsInAllMocks(userId);
	}
}