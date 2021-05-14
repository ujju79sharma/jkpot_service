package com.java.jkpot.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.java.jkpot.dao.UsersDAO;
import com.java.jkpot.model.Users;

@Repository
public class UsersDAOImpl implements UsersDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Users findUsersById(String userId) {

		return mongoTemplate.findOne(Query.query(Criteria.where("userId").is(userId)).addCriteria(Criteria.where("status").is("Active")), Users.class);
	}
	
	@Override
	public Users getUsersFirstNameAndLastName(String userId) {
		
		Query query = new Query();
		
		query.addCriteria(Criteria.where("userId").is(userId)).addCriteria(Criteria.where("statis").is("Active"));
		
		query.fields().include("firstName");
		query.fields().include("lastName");
		
		return mongoTemplate.findOne(query, Users.class);
	}

	@Override
	public Users findUserByEmailAndPhone(String email, String phone) {
		
		if (email != null && phone != null) {
			return mongoTemplate.findOne(Query.query(Criteria.where("email").is(email))
					.addCriteria(Criteria.where("phone").is(phone))
					.addCriteria(Criteria.where("status").is("Active")), Users.class);
		} else if (email != null && phone == null) {
			return mongoTemplate.findOne(Query.query(Criteria.where("email").is(email))
					.addCriteria(Criteria.where("status").is("Active")), Users.class);
		}else if (email == null && phone != null) {
			return mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone))
					.addCriteria(Criteria.where("status").is("Active")), Users.class);
		}else
			return null;
	}
}
