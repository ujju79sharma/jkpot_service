package com.java.jkpot.services;

import org.springframework.http.ResponseEntity;

import com.java.jkpot.api.response.pojo.RestResponse;

public interface MessageService {

	ResponseEntity<RestResponse> createAMessage(int messageId, String senderId, String receiverId, String text,
			String timeStamp, String timeZone, long epochSeconds, String date);

	ResponseEntity<RestResponse> readMessagesOfUser(String senderId, String receiverId, int limit);

	ResponseEntity<RestResponse> showConversationScreenOfUser(String senderId);

}
