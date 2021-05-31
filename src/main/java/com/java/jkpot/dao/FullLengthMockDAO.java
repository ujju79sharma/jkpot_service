package com.java.jkpot.dao;

import java.util.List;

import org.bson.Document;

import com.java.jkpot.model.FullLengthMocks;

public interface FullLengthMockDAO {

	List<FullLengthMocks> findByExamIdAndFullLengthMockId(int examId, int fullLengthMockId, boolean isAnswerRequired);

	List<Document> findTopStudentsInMock();

}
