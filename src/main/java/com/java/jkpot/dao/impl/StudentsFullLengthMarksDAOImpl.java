package com.java.jkpot.dao.impl;

import static com.mongodb.client.model.Accumulators.first;
import static com.mongodb.client.model.Accumulators.max;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.descending;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import com.java.jkpot.dao.StudentsFullLengthMarksDAO;
import com.java.jkpot.utils.DBUtil;
import com.mongodb.client.MongoCollection;

@Repository
public class StudentsFullLengthMarksDAOImpl implements StudentsFullLengthMarksDAO {

	@Override
	public List<Document> findByExamIdAndMockId(int examId, int mockId) {

        MongoCollection<Document> studentsSectionalMarksDoc = DBUtil.databaseInfo().getCollection("students_full_length_mock_marks");

		Bson match = match(and(eq("mockId", mockId), eq("examId", examId)));
	    Bson group = group("$userId", max("totalMarks", "$totalMarks"), first("userName", "$userName"), first("exam","$examName"),
	    		first("mock","$mockName"));
	    Bson project = project(fields(include("userId"), include("userName"), include("totalMarks"), include("mockName"), include("examName")));
	    Bson sort = sort(descending("totalMarks"));
	    Bson limit = limit(10);

	    List<Document> results = studentsSectionalMarksDoc.aggregate(Arrays.asList(match, project, group, sort, limit)).into(new ArrayList<>());
	    return results;
	}

	@Override
	public Document findTopMarksOfStudent(int examId, int mockId, String userId) {

        MongoCollection<Document> studentsSectionalMarksDoc = DBUtil.databaseInfo().getCollection("students_full_length_mock_marks");

		Bson match = match(and(eq("mockId", mockId), eq("examId", examId), eq("userId", userId)));
	    Bson group = group("$userId", max("totalMarks", "$totalMarks"), first("userName", "$userName"), first("exam","$examName"),
	    		first("mock","$mockName"));
	    Bson project = project(fields(include("userId"), include("userName"), include("totalMarks"), include("mockName"), include("examName")));
	    Bson sort = sort(descending("totalMarks"));
	    Bson limit = limit(1);

	    List<Document> results = studentsSectionalMarksDoc.aggregate(Arrays.asList(match, project, group, sort, limit)).into(new ArrayList<>());
	    return (results != null && results.size() > 0) ? results.get(0) : null;
	}
}
