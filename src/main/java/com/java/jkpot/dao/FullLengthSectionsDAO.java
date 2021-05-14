package com.java.jkpot.dao;

import java.util.List;

import com.java.jkpot.model.FullLengthMockSections;

public interface FullLengthSectionsDAO {

	public List<FullLengthMockSections> findByExamId(int examId);

}
