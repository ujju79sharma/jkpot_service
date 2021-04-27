package com.java.jkpot.services;

import java.time.LocalDate;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.jkpot.api.request.pojo.PurchasedMockInfoRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.dao.CountersDAO;
import com.java.jkpot.model.PurchasedMocks;
import com.java.jkpot.model.Users;

@Service
public class PurchasedMockServiceImpl implements PurchasedMockService {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CountersDAO sequence;
	
	@Override
	public ResponseEntity<RestResponse> addPurchasedMockOfUser(PurchasedMockInfoRequest purchasedMockInfoRequest) {
		
		if (purchasedMockInfoRequest.getUserId() != null && purchasedMockInfoRequest.getSubscriptionId() != null
				&& purchasedMockInfoRequest.getExamId() > 0) {

			PurchasedMocks purchasedMock = new PurchasedMocks();
			
			purchasedMock.setUserId(purchasedMockInfoRequest.getUserId());
			purchasedMock.setExamId(purchasedMockInfoRequest.getExamId());
			purchasedMock.setSubscriptionId(purchasedMockInfoRequest.getSubscriptionId());
			purchasedMock.setExamId(purchasedMockInfoRequest.getExamId());
			purchasedMock.setDescription(purchasedMockInfoRequest.getDescription());
			purchasedMock.setPurchasedDate(LocalDate.now());
			purchasedMock.setPurchasedMockId(sequence.getNextSequenceOfField("purchasedMockId"));
			
			Users user = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(purchasedMockInfoRequest.getUserId())), Users.class);
			
			TreeSet<String> subscriptionId = new TreeSet<String>();
			subscriptionId.add(purchasedMockInfoRequest.getSubscriptionId());
			
			user.setSubscriptionIds(subscriptionId);

			mongoTemplate.save(user, "users");
			mongoTemplate.save(purchasedMock, "purchased_mocks");
			
			RestResponse response = new RestResponse("SUCCESS", purchasedMock, 200);
			
			return ResponseEntity.ok(response);
		}else {
			
			RestResponse response = new RestResponse("FAILURE", "missing request body info", 401);

			return ResponseEntity.status(401).body(response);
		}
	}

	@Override
	public ResponseEntity<RestResponse> deletePurchasedMockOfUser(PurchasedMockInfoRequest purchasedMockInfoRequest) {
		
		if (purchasedMockInfoRequest.getPurchasedMockId() > 0 ||
				(purchasedMockInfoRequest.getUserId() != null && purchasedMockInfoRequest.getSubscriptionId() != null)) {

			PurchasedMocks purchasedMock = null;
			
			if (purchasedMockInfoRequest.getPurchasedMockId() > 0)
				purchasedMock = mongoTemplate.findOne(Query.query(Criteria.where("purchasedMockId").is(purchasedMockInfoRequest.getPurchasedMockId())), PurchasedMocks.class);
			else
				purchasedMock = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(purchasedMockInfoRequest.getUserId()))
						.addCriteria(Criteria.where("subscriptionId").is(purchasedMockInfoRequest.getSubscriptionId())), PurchasedMocks.class);
			
			mongoTemplate.remove(purchasedMock, "purchased_mocks");
			
			Users user = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(purchasedMockInfoRequest.getUserId())), Users.class);
			
			TreeSet<String> userSubscriptionId = user.getSubscriptionIds();
			userSubscriptionId.remove(purchasedMockInfoRequest.getSubscriptionId());
			
			user.setSubscriptionIds(userSubscriptionId);

			mongoTemplate.save(user, "users");

			RestResponse response = new RestResponse("SUCCESS", "removed the subscription successfully", 200);
			
			return ResponseEntity.ok(response);
		}else {
			
			RestResponse response = new RestResponse("FAILURE", "missing request body info", 401);

			return ResponseEntity.status(401).body(response);
		}
	}
}