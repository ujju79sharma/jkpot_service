package com.java.jkpot.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.java.jkpot.model.ExamSyllabus;

public interface ExamSyllabusRepository extends MongoRepository<ExamSyllabus, Long>{

	List<ExamSyllabus> findByExamId(int examId);

}
