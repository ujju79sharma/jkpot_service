package com.java.jkpot.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.java.jkpot.model.MockExams;

public interface MockExamRepository extends MongoRepository<MockExams, Long> {

}
