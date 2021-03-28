package com.java.jkpot.model;

import java.time.LocalDate;
import java.util.TreeSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Users {

	@Id
	private String userId;
	private int userTypeId;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String firebaseUID;
	private String location;
	private String macAddress;
	private TreeSet<String> examPreferences;
	private TreeSet<Integer> subscriptionIds;
	private boolean isPrimeUser;
	private String status;
	private LocalDate createdOn;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
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

	public TreeSet<String> getExamPreferences() {
		return examPreferences;
	}

	public void setExamPreferences(TreeSet<String> examPreferencesList) {
		if (this.getExamPreferences() == null || this.getExamPreferences().size() == 0)
			this.examPreferences= (examPreferencesList);
		else
			this.examPreferences.addAll(examPreferencesList);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TreeSet<Integer> getSubscriptionIds() {
		return subscriptionIds;
	}

	public void setSubscriptionIds(TreeSet<Integer> subscriptionIdsList) {
		if (this.getSubscriptionIds() == null || this.getSubscriptionIds().size() == 0)
			this.subscriptionIds = (subscriptionIdsList);
		else
			this.subscriptionIds.addAll(subscriptionIdsList);
	}

	public boolean isPrimeUser() {
		return isPrimeUser;
	}

	public void setPrimeUser(boolean isPrimeUser) {
		this.isPrimeUser = isPrimeUser;
	}

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", userTypeId=" + userTypeId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", phone=" + phone + ", firebaseUID="
				+ firebaseUID + ", location=" + location + ", macAddress=" + macAddress + ", examPreferences="
				+ examPreferences + ", subscriptionIds=" + subscriptionIds + ", isPrimeUser=" + isPrimeUser
				+ ", status=" + status + "]";
	}
}
