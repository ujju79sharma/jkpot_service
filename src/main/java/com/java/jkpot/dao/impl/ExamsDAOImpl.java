package com.java.jkpot.dao.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.java.jkpot.dao.ExamsDAO;
import com.java.jkpot.model.Exams;

@Repository
public class ExamsDAOImpl implements ExamsDAO {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public String getExamName(int examId) {
		Query query = new Query();
		
		query.addCriteria(Criteria.where("examId").is(examId));
		query.fields().include("examName");
		Exams exam = mongoTemplate.findOne(query, Exams.class);

		return exam != null ? exam.getExamName() : null;
	}

	@Override
	public List<Exams> findAllExams(Set<Integer> examIds) {
		
		Query query = new Query();
		
		query.addCriteria(Criteria.where("examId").in(examIds));
		
		return mongoTemplate.find(query, Exams.class);
	}
}
