package com.java.jkpot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.jkpot.api.request.pojo.ExamsStoreDeailsRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.dao.CountersDAO;
import com.java.jkpot.model.ExamConductor;
import com.java.jkpot.model.Exams;
import com.java.jkpot.model.ExamsStore;
import com.java.jkpot.utils.RandomString;

@Service
public class ExamsStoreServiceImpl implements ExamsStoreService{

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CountersDAO sequence;
	
	@Override
	public ResponseEntity<RestResponse> createExamMocksInExamStore(ExamsStoreDeailsRequest examStoreDetailsRequest) {
		
		if (examStoreDetailsRequest != null) {
			
			int examStoreId = examStoreDetailsRequest.getExamStoreId();
			int examId = examStoreDetailsRequest.getExamId();
			int examConductorId = examStoreDetailsRequest.getExamConductorId();
			String examLogo = examStoreDetailsRequest.getExamLogo();
			double price = examStoreDetailsRequest.getPrice();
			String description = examStoreDetailsRequest.getDescription();
			
			if (examStoreId > 0) { //UPDATE

				ExamsStore foundExamStore = mongoTemplate.findOne(Query.query(Criteria.where("examStoreId").is(examStoreId)), ExamsStore.class);
				
				if (examId > 0) {
					foundExamStore.setExamId(examId);
					Exams exam = mongoTemplate.findOne(Query.query(Criteria.where("examId").is(examId)), Exams.class);
					String examName = exam != null ?exam.getExamName():null;
					foundExamStore.setExamName(examName);
				}
				
				if (examConductorId > 0) {
					foundExamStore.setExamConductorId(examConductorId);
					
					ExamConductor examConductor = mongoTemplate.findOne(Query.query(Criteria.where("examConductorId").is(examConductorId)), ExamConductor.class);
					
					String name = examConductor != null ? examConductor.getName() : null;
					
					foundExamStore.setExamConductorName(name);
				}
				
				if (examLogo != null && examLogo.length() > 0)
					foundExamStore.setExamLogo(examLogo);
				
				if (price > 0)
					foundExamStore.setPrice(price*100);
				
				if (description != null)
					foundExamStore.setDescription(description);
				
				mongoTemplate.save(foundExamStore, "exams_store");
				
				RestResponse response = new RestResponse("SUCCESS", foundExamStore, 200);
				
				return ResponseEntity.ok(response);
				
			}else if (examStoreId == 0) {//CREATE
				
				ExamsStore examStore = new ExamsStore();
				
				if (examId > 0) {
					examStore.setExamId(examId);
					
					Exams exam = mongoTemplate.findOne(Query.query(Criteria.where("examId").is(examId)), Exams.class);
					String examName = exam != null ?exam.getExamName():null;
					examStore.setExamName(examName);
				}
				
				if (examConductorId > 0) {

					examStore.setExamConductorId(examConductorId);

					ExamConductor examConductor = mongoTemplate.findOne(Query.query(Criteria.where("examConductorId").is(examConductorId)), ExamConductor.class);

					String name = examConductor != null ? examConductor.getName() : null;
					
					examStore.setExamConductorName(name);
				}

				if (price > 0)
					examStore.setPrice(price*100);

				if (examLogo != null && examLogo.length() > 0)
					examStore.setExamLogo(examLogo);

				if (description != null)
					examStore.setDescription(description);

				examStore.setSubscriptionId(new RandomString(16).nextString());

				examStore.setExamStoreId(sequence.getNextSequenceOfField("examStoreId"));

				mongoTemplate.save(examStore, "exams_store");

				examStore.setSubscriptionId(null);
				RestResponse response = new RestResponse("SUCCESS", examStore, 200);

				return ResponseEntity.ok(response);
			}else {

				RestResponse response = new RestResponse("FAILURE", "Provide API Data in the request", 400);
	
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		}else {
			RestResponse response = new RestResponse("FAILURE", "Provide API Data in the request", 400);
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@Override
	public ResponseEntity<RestResponse> findAllExamsMocksInExamStore() {
		
		List<ExamsStore> examStores = mongoTemplate.findAll(ExamsStore.class);
		
		
		RestResponse response = new RestResponse("SUCCESS", examStores, 200);
		
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<RestResponse> findExamMockInExamStore(int examStoreId) {
		
		if (examStoreId > 0) {
			
			ExamsStore examMock = mongoTemplate.findOne(Query.query(Criteria.where("examStoreId").is(examStoreId)),ExamsStore.class);
			
			if (examMock != null) {
				RestResponse response = new RestResponse("SUCCESS", examMock, 200);
				
				return ResponseEntity.ok(response);
			}else {

				RestResponse response = new RestResponse("FAILURE", examMock, 204);
				
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
			}
		}else {
			RestResponse response = new RestResponse("FAILURE", "Provide API Data in the request", 400);
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@Override
	public ResponseEntity<RestResponse> deleteExamsMocksInExamStore(int examsStoreId) {
		
		if (examsStoreId > 0) {
			
			mongoTemplate.findAndRemove(Query.query(Criteria.where("examStoreId").is(examsStoreId)),ExamsStore.class);
			
			RestResponse response = new RestResponse("SUCCESS", "deleted exam successfully", 200);
			
			return ResponseEntity.ok(response);
		}else {
			RestResponse response = new RestResponse("FAILURE", "Provide API Data in the request", 400);
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
}
