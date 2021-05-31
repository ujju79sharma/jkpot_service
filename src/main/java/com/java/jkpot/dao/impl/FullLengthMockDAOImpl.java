package com.java.jkpot.dao.impl;

import java.util.List;

import org.bson.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.java.jkpot.dao.FullLengthMockDAO;
import com.java.jkpot.model.FullLengthMocks;
import com.java.jkpot.model.StudentsFullLengthMockMarks;


@Repository
public class FullLengthMockDAOImpl implements FullLengthMockDAO{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<FullLengthMocks> findByExamIdAndFullLengthMockId(int examId, int mockId, boolean isAnswerRequired) {
		
		Query query = new Query();

		query.addCriteria(Criteria.where("examId").is(examId)).addCriteria(Criteria.where("mockId").is(mockId));

		if (isAnswerRequired == false)
			query.fields().exclude("answer");

		return mongoTemplate.find(query, FullLengthMocks.class);
	}

	@Override
	public List<Document> findTopStudentsInMock() {

		Aggregation agg = Aggregation.newAggregation(Aggregation.project("userId", "userName","totalMarks") 
//			        .and("correctAnswers").plus("incorrectAnswers").plus("skippedQuestion").as("totalMarksFound"),
			        .andExpression("correctAnswers+incorrectAnswers+skippedQuestion").as("totalMarksFound"), 
			        Aggregation.group("userId").sum("totalMarksFound").as("totalMarksFound").sum("totalMarks").as("totalMarksGot").first("userName").as("name"),
			        Aggregation.sort(Direction.DESC, "totalMarksGot"),
			        Aggregation.limit(10));

		AggregationResults<Document> result = mongoTemplate.aggregate(agg, StudentsFullLengthMockMarks.class, Document.class);

		return result.getMappedResults();
	}
}