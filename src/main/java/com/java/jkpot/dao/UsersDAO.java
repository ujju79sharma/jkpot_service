package com.java.jkpot.dao;

import com.java.jkpot.model.Users;

public interface UsersDAO {

	Users findUsersById(String userId);
	
	Users findUserByEmailAndPhone(String email, String phone);
}
