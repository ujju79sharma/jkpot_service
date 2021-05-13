package com.java.jkpot.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.java.jkpot.dao.StudentsSectionalMarksDAO;
import com.java.jkpot.model.StudentsSectionalMarks;

@Repository
public class StudentsSectionalMarksDAOImpl implements StudentsSectionalMarksDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<StudentsSectionalMarks> findStudentsSectionalMocksByStudentId(String userId) {
		
		Query query = new Query();

		query.addCriteria(Criteria.where("userId").is(userId));
		query.fields().include("sectionalId");
		query.fields().include("subSectionalId");
		query.fields().include("examId");

		return mongoTemplate.find(query, StudentsSectionalMarks.class);
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
}
