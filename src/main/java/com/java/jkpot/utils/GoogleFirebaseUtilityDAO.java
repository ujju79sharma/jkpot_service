package com.java.jkpot.utils;

public interface GoogleFirebaseUtilityDAO {

	public void sendNotificationToUser (String userName, String senderId, String receiverFcmToken, String message, String messageBody, String deepLink); 

}
