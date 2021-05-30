package com.java.jkpot.dao;

import java.util.List;

import org.bson.Document;

public interface StudentsFullLengthMarksDAO {

	List<Document> findByExamIdAndMockId(int examId, int mockId);

	Document findTopMarksOfStudent(int examId, int mockId, String userId);

	List<Document> fetchHighestMarksOfStudents(int examId, int fullLengthMockId);

}
