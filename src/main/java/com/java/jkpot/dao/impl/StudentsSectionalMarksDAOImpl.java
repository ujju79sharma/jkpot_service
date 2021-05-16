package com.java.jkpot.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.java.jkpot.dao.StudentsSectionalMarksDAO;
import com.java.jkpot.model.StudentsSectionalMarks;
import com.java.jkpot.utils.DBUtil;
import com.mongodb.client.MongoCollection;

import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.descending;

@Repository
public class StudentsSectionalMarksDAOImpl implements StudentsSectionalMarksDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<Document> findStudentsSectionalMocksByStudentId(String userId) {

        MongoCollection<Document> studentsSectionalMarksDoc = DBUtil.databaseInfo().getCollection("students_sectional_marks");

		Bson match = match(eq("userId", userId));
	    Bson group = group("$subSectionName", first("section","$sectionalName"),
	    		first("sectionalId", "$sectionalId"), first("subSectionalId", "$subSectionalId"), first("examId", "$examId"));
	    Bson project = project(fields(include("userId"), include("userId"), include("sectionalName"), include("subSectionName"),
	    		include("subSectionalId"), include("sectionalId"), include("examId")));
	    Bson sort = sort(descending("subSectionalName"));

	    List<Document> results = studentsSectionalMarksDoc.aggregate(Arrays.asList(match, project, group, sort)).into(new ArrayList<>());
	    return (results != null && results.size() > 0) ? results : null;
	}

	@Override
	public List<StudentsSectionalMarks> findByExamIdAndSectionalIdAndSubSectionalId(int examId, int sectionalId,
			int subSectionalId) {
		
		Query query = new Query();

		query.addCriteria(Criteria.where("examId").is(examId));
		query.addCriteria(Criteria.where("sectionalId").is(sectionalId));
		query.addCriteria(Criteria.where("subSectionalId").is(subSectionalId));
		
		query.fields().include("sectionalId");
		query.fields().include("subSectionalId");
		query.fields().include("examId");
		query.fields().include("userId");
		query.fields().include("totalMarks");
		query.fields().include("userName");
		query.fields().include("sectionalName");
		query.fields().include("subSectionName");

		return mongoTemplate.find(query, StudentsSectionalMarks.class);
	}

	@Override
	public List<Document> fetchHighestMarksOfStudents(int examId, int sectionalId, int subSectionalId) {

        MongoCollection<Document> studentsSectionalMarksDoc = DBUtil.databaseInfo().getCollection("students_sectional_marks");

		Bson match = match(and(eq("sectionalId", sectionalId), eq("subSectionalId", subSectionalId), eq("examId", examId)));
	    Bson group = group("$userId", max("totalMarks", "$totalMarks"), first("userName", "$userName"), first("section","$sectionalName"),
	    		first("subSection","$subSectionName"));
	    Bson project = project(fields(include("userId"), include("userName"), include("totalMarks"), include("sectionalName"), include("subSectionName")));
	    Bson sort = sort(descending("totalMarks"));
	    Bson limit = limit(10);

	    List<Document> results = studentsSectionalMarksDoc.aggregate(Arrays.asList(match, project, group, sort, limit)).into(new ArrayList<>());
	    return results;
	}

	public Document fetchHighestMarksOfStudent(int examId, int sectionalId, int subSectionalId, String userId) {

        MongoCollection<Document> studentsSectionalMarksDoc = DBUtil.databaseInfo().getCollection("students_sectional_marks");

		Bson match = match(and(eq("sectionalId", sectionalId), eq("subSectionalId", subSectionalId), eq("examId", examId), eq("userId", userId)));
	    Bson group = group("$userId", max("totalMarks", "$totalMarks"), first("userName", "$userName"), first("section","$sectionalName"),
	    		first("subSection","$subSectionName"));
	    Bson project = project(fields(include("userId"), include("userName"), include("totalMarks"), include("sectionalName"), include("subSectionName")));
	    Bson sort = sort(descending("totalMarks"));
	    Bson limit = limit(1);

	    List<Document> results = studentsSectionalMarksDoc.aggregate(Arrays.asList(match, project, group, sort, limit)).into(new ArrayList<>());
	    return (results != null && results.size() > 0) ? results.get(0) : null;
	}
}
