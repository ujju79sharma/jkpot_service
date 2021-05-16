package com.java.jkpot.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.java.jkpot.model.StudentsSectionalMarks;

public interface StudentsSectionalMarksRepository extends MongoRepository<StudentsSectionalMarks, Long> {

	List<StudentsSectionalMarks> findBySectionalIdAndSubSectionalId(int sectionalId, int subSectionalId);

	List<StudentsSectionalMarks> findByExamIdAndSectionalIdAndSubSectionalId(int examId, int sectionalId, int subSectionalId);
}