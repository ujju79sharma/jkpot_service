package com.java.jkpot.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.java.jkpot.model.StudentsFullLengthMockMarks;

public interface StudentFullLengthMockMarksRepository extends MongoRepository<StudentsFullLengthMockMarks, Integer> {

	@Aggregation(pipeline = "{$match:{$and:[{mockId: ?1}, {examId:?0}]}},{$group:{_id:\"$userId\",totalMarks:{$max:\"$totalMarks\"}, name:{$first:\"$userName\"}}},{$sort:{totalMarks:-1}}")
	List<StudentsFullLengthMockMarks> findByExamIdAndMockId(int examId, int mockId);

}
