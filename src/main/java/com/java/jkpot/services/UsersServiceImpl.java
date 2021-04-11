package com.java.jkpot.services;

import java.time.LocalDate;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.jkpot.api.request.pojo.UserDetailsRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.dao.UsersDAO;
import com.java.jkpot.model.Users;

@Service
public class UsersServiceImpl implements UsersService{

	@Autowired
	private UsersDAO usersDAO;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public ResponseEntity<RestResponse> createAUser(UserDetailsRequest createUserDetails) {

		Users user = usersDAO.findUserByEmailAndPhone(null, createUserDetails.getPhone()); // find the user.

		if (user == null) {

			Users theUser = new Users();

			theUser.setFirstName(createUserDetails.getFirstName());
			theUser.setLastName(createUserDetails.getLastName());
			theUser.setEmail(createUserDetails.getEmail());
			theUser.setPhone(createUserDetails.getPhone());
			theUser.setFirebaseUID(createUserDetails.getFirebaseUID());
			theUser.setUserTypeId(createUserDetails.getUserTypeId());
			theUser.setMacAddress(createUserDetails.getMacAddress());
			theUser.setLocation(createUserDetails.getLocation());
			
			if (createUserDetails.getExamPreferences() != null && createUserDetails.getExamPreferences().size() > 0) {
				TreeSet<String> examPreferences = new TreeSet<String>(createUserDetails.getExamPreferences());
				theUser.setExamPreferences(examPreferences);
			}

			theUser.setStatus("Active");
			theUser.setPrimeUser(false);
			
			if (createUserDetails.getSubscriptionIds() != null && createUserDetails.getSubscriptionIds().size() > 0) {
				TreeSet<Integer> subscriptionIds = new TreeSet<>(createUserDetails.getSubscriptionIds());
				theUser.setSubscriptionIds(subscriptionIds);
			}
	
			if ((theUser.getEmail() != null || theUser.getPhone() != null) && (theUser.getFirebaseUID() != null)) {
				theUser.setUserId(createUserDetails.getUserId());
				theUser.setCreatedOn(LocalDate.now());
				mongoTemplate.save(theUser, "users"); // save the object
			}

			RestResponse restResponse = new RestResponse("SUCCESS", theUser, 200);

			return ResponseEntity.status(200).body(restResponse);

		}else {

			mongoTemplate.remove(user, "users"); // remove existing object from database
			Users theUser = user;

			if (createUserDetails.getUserId() != null && ! createUserDetails.getUserId().equalsIgnoreCase(user.getUserId()))
				theUser.setUserId(createUserDetails.getUserId());
			if (createUserDetails.getFirstName() != null)
				theUser.setFirstName(createUserDetails.getFirstName());
			if (createUserDetails.getLastName() != null)
				theUser.setLastName(createUserDetails.getLastName());				
			if (createUserDetails.getEmail() != null)
				theUser.setEmail(createUserDetails.getEmail());
			if (createUserDetails.getPhone() != null)
				theUser.setPhone(createUserDetails.getPhone());
			if (createUserDetails.getFirebaseUID() != null)
				theUser.setFirebaseUID(createUserDetails.getFirebaseUID());
			if (createUserDetails.getUserTypeId() > 0)
				theUser.setUserTypeId(createUserDetails.getUserTypeId());
			if (createUserDetails.getMacAddress() != null)
				theUser.setMacAddress(createUserDetails.getMacAddress());
			if (createUserDetails.getExamPreferences() != null && createUserDetails.getExamPreferences().size() > 0) {
				TreeSet<String> examPreferences = new TreeSet<String>(createUserDetails.getExamPreferences());
				theUser.setExamPreferences(examPreferences);
			}
			if (createUserDetails.getLocation() != null && createUserDetails.getLocation().length() > 0) {
				theUser.setLocation(createUserDetails.getLocation());
			}
			if (createUserDetails.getIsPrimeUser() == true || createUserDetails.getIsPrimeUser() == false)
				theUser.setPrimeUser(createUserDetails.getIsPrimeUser());
			if (createUserDetails.getSubscriptionIds() != null && createUserDetails.getSubscriptionIds().size() > 0) {
				TreeSet<Integer> subscriptionIds = new TreeSet<>(createUserDetails.getSubscriptionIds());
				theUser.setSubscriptionIds(subscriptionIds);
			}

			mongoTemplate.save(theUser, "users"); // save the new object

			RestResponse restResponse = new RestResponse("SUCCESS", theUser, 200);

			return ResponseEntity.status(200).body(restResponse);
		}
	}

	@Override
	public ResponseEntity<RestResponse> findAUser(String userId) {

		if (userId != null && userId.length() > 0) {
			Users theUser = usersDAO.findUsersById(userId);

			if (theUser != null) {

				RestResponse restResponse = new RestResponse("SUCCESS", theUser, 200);

				restResponse.setStatusCode(200);
				restResponse.setMessage("SUCCESS");
				restResponse.setData(theUser);

				return ResponseEntity.status(200).body(restResponse);

			}else {
				RestResponse restResponse = new RestResponse("FAILURE", "User does not exist.", 404);

				return ResponseEntity.status(403).body(restResponse);
			}

		}else {

			RestResponse restResponse = new RestResponse("FAILURE", "Please provide a valid userId.", 403);

			return ResponseEntity.status(403).body(restResponse);
		}
	}

	@Override
	public ResponseEntity<RestResponse> deleteAUser(String userId) {

		if (userId != null && userId.length() > 0) {
			Users theUser = usersDAO.findUsersById(userId);
			
			if (theUser != null) {
				
				theUser.setStatus("Inactive");
				mongoTemplate.save(theUser, "users"); //change the status of the user to Inactive
				
				RestResponse restResponse = new RestResponse("SUCCESS", theUser, 200);
				
				return ResponseEntity.status(200).body(restResponse);
				
			}else {
				RestResponse restResponse = new RestResponse("FAILURE", "User does not exist.", 404);

				return ResponseEntity.status(403).body(restResponse);
			}
			
		}else {
			
			RestResponse restResponse = new RestResponse("FAILURE", "Please provide a valid userId.", 403);

			return ResponseEntity.status(403).body(restResponse);
		}
	}
}
