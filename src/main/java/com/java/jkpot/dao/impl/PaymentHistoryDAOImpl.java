package com.java.jkpot.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.java.jkpot.dao.PurchaseHistoryDAO;
import com.java.jkpot.model.PurchaseHistory;

@Repository
public class PaymentHistoryDAOImpl implements PurchaseHistoryDAO {

	@Autowired
	private MongoTemplate mongotemplate;

	@Override
	public PurchaseHistory checkAlreadyPurchasedByUser(String userId, int examId) {

		Query query = new Query();

		query.addCriteria(Criteria.where("userId").is(userId)).addCriteria(Criteria.where("examId").is(examId));
		query.fields().include("purchaseHistoryId");
		return mongotemplate.findOne(query, PurchaseHistory.class);
	}

	@Override
	public List<PurchaseHistory> fetchPurchaseHistoryOfUser(String userId) {
		
		Query query = new Query();

		query.addCriteria(Criteria.where("userId").is(userId)).addCriteria(Criteria.where("status").is("Purchased"));
		query.fields().exclude("purchaseHistoryId");
		query.fields().exclude("subscriptionId");
		return mongotemplate.find(query, PurchaseHistory.class);
	}
}
