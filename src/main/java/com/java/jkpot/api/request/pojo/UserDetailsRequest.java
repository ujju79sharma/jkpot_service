package com.java.jkpot.api.request.pojo;

import java.util.List;

public class UserDetailsRequest {

	private long userId;
	private String firstName;
	private String lastName;
	private int userTypeId;
	private String email;
	private String phone;
	private String password;
	private String firebaseUID;
	private String location;
	private String macAddress;
	private List<String> examPreferences;
	private List<Integer> subscriptionIds;
	private boolean isPrimeUser;

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

	public void setExamPreferences(List<String> examPreferencesList) {

		if (this.getExamPreferences() == null || this.getExamPreferences().size() == 0)
			this.examPreferences= (examPreferencesList);
		else
			this.examPreferences.addAll(examPreferencesList);
	}

	public List<Integer> getSubscriptionIds() {
		return subscriptionIds;
	}

	public void setSubscriptionIds(List<Integer> subscriptionIdsList) {
		if (this.getSubscriptionIds() == null || this.getSubscriptionIds().size() == 0)
			this.subscriptionIds = (subscriptionIdsList);
		else
			this.subscriptionIds.addAll(subscriptionIdsList);
	}

	public boolean getIsPrimeUser() {
		return isPrimeUser;
	}

	public void setIsPrimeUser(boolean isPrimeUser) {
		this.isPrimeUser = isPrimeUser;
	}
}