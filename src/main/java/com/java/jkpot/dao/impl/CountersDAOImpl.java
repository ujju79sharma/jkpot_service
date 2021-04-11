package com.java.jkpot.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.java.jkpot.dao.CountersDAO;
import com.java.jkpot.model.Counters;

@Repository
public class CountersDAOImpl implements CountersDAO {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public int getNextSequenceOfField(String fieldName) {

		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(fieldName));
		Update update = new Update().inc("sequence", 1);

		return (int)mongoTemplate.findAndModify(query, update, Counters.class).getSequence();
	}

}
