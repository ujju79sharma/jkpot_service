package com.java.jkpot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.dao.CountersDAO;
import com.java.jkpot.model.Exams;

@Service
public class ExamsServiceImpl implements ExamsService {

	@Autowired
	private CountersDAO sequence;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public ResponseEntity<RestResponse> createExam(int examId, String examName, String examLogo) {

		if (examId > 0) {
			Exams foundExam = mongoTemplate.findOne(Query.query(Criteria.where("examId").is(examId)), Exams.class);
			
			if (foundExam != null) {
				
				if (examName != null)
					foundExam.setExamName(examName);
				if (examLogo != null)
					foundExam.setExamLogo(examLogo);

				mongoTemplate.save(foundExam, "exams");
				
				RestResponse response = new RestResponse("SUCCESS", foundExam, 200);
				
				return ResponseEntity.ok(response);
			} else {
				RestResponse response = new RestResponse("NOT FOUND", "examId does not exist.", 404);
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} else if (examId == 0) {
			
			Exams exam = new Exams();
			
			if (examLogo != null)
				exam.setExamLogo(examLogo);
			
			if (examName != null)
				exam.setExamName(examName);
			
			exam.setExamId(sequence.getNextSequenceOfField("examId"));
			
			mongoTemplate.save(exam, "exams");

			RestResponse response = new RestResponse("SUCCESS", exam, 200);
			
			return ResponseEntity.ok(response);
		}
		return null;
	}

	@Override
	public ResponseEntity<RestResponse> findExamById(int examId) {
		
		if (examId > 0) {
			Exams foundExam = mongoTemplate.findOne(Query.query(Criteria.where("examId").is(examId)), Exams.class);
			
			if (foundExam != null) {
				RestResponse response = new RestResponse("SUCCESS", foundExam, 200);
				
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}else {
				RestResponse response = new RestResponse("NOT FOUND", "examId does not exist", 404);
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}		
		}else {
			RestResponse response = new RestResponse("NOT FOUND", "examId does not exist", 404);
					
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	@Override
	public ResponseEntity<RestResponse> findAllExams() {
		
		List<Exams> foundExams = mongoTemplate.findAll(Exams.class);
		
		if (foundExams != null && foundExams.size() > 0) {
			RestResponse response = new RestResponse("SUCCESS", foundExams, 200);
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}else {
			RestResponse response = new RestResponse("NO DATA", "No exams added.", 200);
			
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response);
		}
	}

	@Override
	public ResponseEntity<RestResponse> deleteExamData(int examId) {
		
		if (examId > 0) {
			Exams foundExam = mongoTemplate.findOne(Query.query(Criteria.where("examId").is(examId)), Exams.class);
			
			if (foundExam != null) {
				
				mongoTemplate.remove(foundExam, "exams");
				
				RestResponse response = new RestResponse("SUCCESS", "exam deleted successfully", 200);
				
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}else {
				RestResponse response = new RestResponse("NOT FOUND", "examId does not exist", 404);
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}		
		}else {
			RestResponse response = new RestResponse("NOT FOUND", "examId does not exist", 404);
					
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}
}
