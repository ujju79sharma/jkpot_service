package com.java.jkpot.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.java.jkpot.dao.FullLengthMockDAO;
import com.java.jkpot.model.FullLengthMocks;

@Repository
public class FullLengthMockDAOImpl implements FullLengthMockDAO{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<FullLengthMocks> findByExamIdAndFullLengthMockId(int examId, int mockId) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("examId").is(examId)).addCriteria(Criteria.where("mockId").is(mockId));
//		query.fields().exclude("answer");
		return mongoTemplate.find(query, FullLengthMocks.class);
	}
}
