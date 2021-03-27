package com.java.jkpot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.jkpot.api.request.pojo.UserDetailsRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.dao.CountersDAO;
import com.java.jkpot.dao.UsersDAO;
import com.java.jkpot.model.Users;

@Service
public class UsersServiceImpl implements UsersService{

	@Autowired
	private UsersDAO usersDAO;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CountersDAO sequence;

	@Override
	public ResponseEntity<RestResponse> createAUser(UserDetailsRequest createUserDetails) {

		long userId = createUserDetails.getUserId();

		if (userId == 0) {

			Users theUser = new Users();

			theUser.setFirstName(createUserDetails.getFirstName());
			theUser.setLastName(createUserDetails.getLastName());
			theUser.setEmail(createUserDetails.getEmail());
			theUser.setPhone(createUserDetails.getPhone());
			theUser.setPassword(null);
			theUser.setFirebaseUID(createUserDetails.getFirebaseUID());
			theUser.setUserTypeId(createUserDetails.getUserTypeId());
			theUser.setMacAddress(createUserDetails.getMacAddress());
			theUser.setLocation(createUserDetails.getLocation());
			theUser.setExamPreferences(createUserDetails.getExamPreferences());
			theUser.setStatus("Active");

			if ((theUser.getEmail() != null || theUser.getPhone() != null) && (theUser.getFirebaseUID() != null)) {
				theUser.setUserId(sequence.getNextSequenceOfField("userId"));
				mongoTemplate.save(theUser, "users"); // save the object
			}

			RestResponse restResponse = new RestResponse("SUCCESS", theUser, 200);

			return ResponseEntity.status(200).body(restResponse);
		}else if (userId > 0){

			Users foundUser = usersDAO.findUsersById(userId);

			if (foundUser != null) {

				Users theUser = foundUser;

				if (createUserDetails.getFirstName() != null)
					theUser.setFirstName(createUserDetails.getFirstName());
				if (createUserDetails.getLastName() != null)
					theUser.setLastName(createUserDetails.getLastName());				
				if (createUserDetails.getEmail() != null)
					theUser.setEmail(createUserDetails.getEmail());
				if (createUserDetails.getPhone() != null)
					theUser.setPhone(createUserDetails.getPhone());
				if (createUserDetails.getPassword() != null)
					theUser.setPassword(null);
				if (createUserDetails.getFirebaseUID() != null)
					theUser.setFirebaseUID(createUserDetails.getFirebaseUID());
				if (createUserDetails.getUserTypeId() > 0)
					theUser.setUserTypeId(createUserDetails.getUserTypeId());
				if (createUserDetails.getMacAddress() != null)
					theUser.setMacAddress(createUserDetails.getMacAddress());
				if (createUserDetails.getExamPreferences() != null && createUserDetails.getExamPreferences().size() > 0)
					theUser.setExamPreferences(createUserDetails.getExamPreferences());
				if (createUserDetails.getLocation() != null && createUserDetails.getLocation().length() > 0)
					theUser.setLocation(createUserDetails.getLocation());

				mongoTemplate.save(theUser, "users"); // save the object

				RestResponse restResponse = new RestResponse("SUCCESS", theUser, 200);

				return ResponseEntity.status(200).body(restResponse);
			}else {

				RestResponse restResponse = new RestResponse("FAILURE", "User does not exist.", 404);

				return ResponseEntity.status(404).body(restResponse);
			}
		}else {

			RestResponse restResponse = new RestResponse("FAILURE", "User does not exist.", 404);

			return ResponseEntity.status(404).body(restResponse);
		}
	}

	@Override
	public ResponseEntity<RestResponse> findAUser(long userId) {

		if (userId > 0) {
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
	public ResponseEntity<RestResponse> deleteAUser(long userId) {

		if (userId > 0) {
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
