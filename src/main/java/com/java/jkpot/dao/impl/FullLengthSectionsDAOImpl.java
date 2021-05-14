package com.java.jkpot.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.java.jkpot.dao.FullLengthSectionsDAO;
import com.java.jkpot.model.FullLengthMockSections;

@Repository
public class FullLengthSectionsDAOImpl implements FullLengthSectionsDAO{

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<FullLengthMockSections> findByExamId(int examId) {

		Query query = new Query();
		query.addCriteria(Criteria.where("examId").is(examId));
		return mongoTemplate.find(query, FullLengthMockSections.class);
	}
	
}
