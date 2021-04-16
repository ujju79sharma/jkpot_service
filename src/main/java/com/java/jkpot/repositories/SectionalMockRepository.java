package com.java.jkpot.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.java.jkpot.model.SectionalMocks;

public interface SectionalMockRepository extends MongoRepository<SectionalMocks, Long>{

	List<SectionalMocks> findBySectionalIdAndSubSectionalId(int sectionalId, int subSectionalId);

}
