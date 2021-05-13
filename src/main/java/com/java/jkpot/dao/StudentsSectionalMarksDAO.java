package com.java.jkpot.dao;

import java.util.List;

import com.java.jkpot.model.StudentsSectionalMarks;

public interface StudentsSectionalMarksDAO {

	List<StudentsSectionalMarks> findStudentsSectionalMocksByStudentId(String userId);

	List<StudentsSectionalMarks> findByExamIdAndSectionalIdAndSubSectionalId(int examId, int sectionalId, int subSectionalId);
}
