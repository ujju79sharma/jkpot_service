package com.java.jkpot.dao;

import java.util.List;

import org.bson.Document;

import com.java.jkpot.model.StudentsSectionalMarks;

public interface StudentsSectionalMarksDAO {

	List<Document> findStudentsSectionalMocksByStudentId(String userId);

	List<StudentsSectionalMarks> findByExamIdAndSectionalIdAndSubSectionalId(int examId, int sectionalId, int subSectionalId);
	
	List<Document> fetchHighestMarksOfStudents (int examId, int sectionalId, int subSectionalId);

	Document fetchHighestMarksOfStudent(int examId, int sectionalId, int subSectionalId, String userId);
}
