package com.java.jkpot.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.java.jkpot.dao.ExamsStoreDAO;
import com.java.jkpot.model.ExamsStore;

@Repository
public class ExamsStoreDAOImpl implements ExamsStoreDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<ExamsStore> findAllExamsInTheStore() {

		Query query = new Query();
		query.fields().exclude("subscriptionId");
		return mongoTemplate.find(query, ExamsStore.class);
	}

}
