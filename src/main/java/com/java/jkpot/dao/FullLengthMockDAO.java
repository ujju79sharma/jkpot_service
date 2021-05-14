package com.java.jkpot.dao;

import java.util.List;

import com.java.jkpot.model.FullLengthMocks;

public interface FullLengthMockDAO {

	List<FullLengthMocks> findByExamIdAndFullLengthMockId(int examId, int fullLengthMockId);

	
}
