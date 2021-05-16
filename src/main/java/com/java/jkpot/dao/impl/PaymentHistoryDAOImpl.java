package com.java.jkpot.dao.impl;

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
}
