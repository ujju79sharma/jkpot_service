package com.java.jkpot.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.java.jkpot.api.request.pojo.UserDetailsRequest;
import com.java.jkpot.api.response.pojo.RestResponse;

public interface UsersService {

	ResponseEntity<RestResponse> createAUser(@RequestBody UserDetailsRequest createUserDetails);

	ResponseEntity<RestResponse> findAUser(String userId);
	
	ResponseEntity<RestResponse> deleteAUser(String userId);

}
