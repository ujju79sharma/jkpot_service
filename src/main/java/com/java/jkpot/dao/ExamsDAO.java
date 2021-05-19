package com.java.jkpot.dao;

import java.util.List;
import java.util.Set;

import com.java.jkpot.model.Exams;

public interface ExamsDAO {

	String getExamName(int examId);

	List<Exams> findAllExams(Set<Integer> examIds);
}
