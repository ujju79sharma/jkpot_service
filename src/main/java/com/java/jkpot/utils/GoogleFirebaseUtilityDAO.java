package com.java.jkpot.utils;

public interface GoogleFirebaseUtilityDAO {

	public void sendNotificationToUser (String userName, long senderId, long receiverId, String profilePhoto, String receiverFcmToken, String message, String messageBody, String deepLink); 

}
