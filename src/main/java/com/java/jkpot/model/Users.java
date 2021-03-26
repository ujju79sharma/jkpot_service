package com.java.jkpot.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Users {

	@Id
	private long userId;
	private int userTypeId;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String password;
	private String firebaseUID;
	private String location;
	private String macAddress;
	private List<String> examPreferences;
	private String status;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirebaseUID() {
		return firebaseUID;
	}

	public void setFirebaseUID(String firebaseUID) {
		this.firebaseUID = firebaseUID;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}	

	public List<String> getExamPreferences() {
		return examPreferences;
	}

	public void setExamPreferences(List<String> examPreferences) {
		this.examPreferences = examPreferences;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", userTypeId=" + userTypeId + ", email=" + email + ", password=" + password
				+ ", firebaseUID=" + firebaseUID + ", location=" + location + ", macAddress=" + macAddress + "]";
	}
}
