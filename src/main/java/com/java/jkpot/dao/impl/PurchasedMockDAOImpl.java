package com.java.jkpot.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.java.jkpot.dao.PurchasedMockDAO;
import com.java.jkpot.model.PurchasedMocks;

@Repository
public class PurchasedMockDAOImpl implements PurchasedMockDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public PurchasedMocks checkAlreadyPurchasedByUser(String userId, int examId) {
		
		Query query = new Query();

		query.addCriteria(Criteria.where("userId").is(userId)).addCriteria(Criteria.where("examId").is(examId))
			.addCriteria(Criteria.where("status").is("Purchased"));
		query.fields().include("purchasedMockId");

		return mongoTemplate.findOne(query, PurchasedMocks.class);
	}

}
