package com.java.jkpot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.jkpot.api.request.pojo.UserDetailsRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.services.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UsersService usersService;

	@PostMapping(value = "/create")
	public ResponseEntity<RestResponse> createAUser(@RequestBody UserDetailsRequest createUserDetails) {

		return usersService.createAUser(createUserDetails);
	}
	
	@GetMapping(value = "/find/{userId}")
	public ResponseEntity<RestResponse> findAUser(@PathVariable String userId) {

		return usersService.findAUser(userId);
	}

	@DeleteMapping(value = "/delete/{userId}")
	public ResponseEntity<RestResponse> deleteAUser(@PathVariable String userId) {

		return usersService.deleteAUser(userId);
	}
	
	@PostMapping(value = "/update/fcmtoken")
	public ResponseEntity<RestResponse> updateUserFCMToken(@RequestBody UserDetailsRequest createUserDetails) {

		return usersService.updateUserFCMTokenUser(createUserDetails);
	}
	
}
