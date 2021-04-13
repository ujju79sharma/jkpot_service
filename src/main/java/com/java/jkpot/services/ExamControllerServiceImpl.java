package com.java.jkpot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.dao.CountersDAO;
import com.java.jkpot.model.ExamConductor;

@Service
public class ExamControllerServiceImpl implements ExamConductorsService {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CountersDAO sequence;
	
	@Override
	public ResponseEntity<RestResponse> createExamConductor(int examConductorId, String name, String logo) {

		if (examConductorId > 0) {

			ExamConductor examConductor = mongoTemplate.findOne(Query.query(Criteria.where("examControllerId").is(examConductorId)), ExamConductor.class);

			if (examConductor != null) {

				if (name != null)
					examConductor.setName(name);
				
				if (logo != null)
					examConductor.setLogo(logo);
				
				mongoTemplate.save(examConductor, "exam_conductors");
				
				RestResponse response = new RestResponse("SUCCESS", examConductor, 200);
				
				return ResponseEntity.ok(response);
			}else {
				
				RestResponse response = new RestResponse("FAILURE", examConductor, 404);
				
				return ResponseEntity.status(404).body(response);
			}
				
		}else if (examConductorId == 0) {
			
			ExamConductor examConductor = new ExamConductor();

			if (name != null)
				examConductor.setName(name);
			
			if (logo != null)
				examConductor.setLogo(logo);
			
			examConductor.setExamConductorId(sequence.getNextSequenceOfField("examConductorId"));
			
			mongoTemplate.save(examConductor, "exam_conductors");
			
			RestResponse response = new RestResponse("SUCCESS", examConductor, 200);
			
			return ResponseEntity.ok(response);
		}else
			return null;
	}

	@Override
	public ResponseEntity<RestResponse> fetchExamConductor(int examConductorId) {
		
		if (examConductorId > 0) {
			
			ExamConductor examConductor = mongoTemplate.findOne(Query.query(Criteria.where("examControllerId").is(examConductorId)), ExamConductor.class);

			if (examConductor != null) {

				RestResponse response = new RestResponse("SUCCESS", examConductor, 200);

				return ResponseEntity.ok(response);
			}else {
				
				RestResponse response = new RestResponse("FAILURE", examConductor, 404);
				
				return ResponseEntity.status(404).body(response);
			}
				
		}else {
			
			RestResponse response = new RestResponse("FAILURE", "examControllerId not provided", 404);
			
			return ResponseEntity.status(404).body(response);
		}
	}

	@Override
	public ResponseEntity<RestResponse> fetchExamConductors() {
		
		List<ExamConductor> examConductors = mongoTemplate.findAll(ExamConductor.class);


		RestResponse response = new RestResponse("SUCCESS", examConductors, 200);

		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<RestResponse> deleteExamConductor(int examConductorId) {
		
		if (examConductorId > 0) {
			
			ExamConductor examConductor = mongoTemplate.findOne(Query.query(Criteria.where("examControllerId").is(examConductorId)), ExamConductor.class);

			if (examConductor != null) {

				mongoTemplate.remove(examConductor, "exam_conductors");
				
				RestResponse response = new RestResponse("SUCCESS", "Exam Conductor deleted successfully", 200);

				return ResponseEntity.ok(response);
			}else {
				
				RestResponse response = new RestResponse("FAILURE", "examcontrollerid does not exist", 404);
				
				return ResponseEntity.status(404).body(response);
			}

		}else {
			
			RestResponse response = new RestResponse("SUCCESS", "examControllerId not provided", 401);
			
			return ResponseEntity.ok(response);
		}
	}
}
