package com.java.jkpot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.jkpot.api.request.pojo.MessageDetailsRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.services.MessageService;

@RestController
@RequestMapping("/message")
public class MessagesController {

	@Autowired
	private MessageService messageService;
	
	@PostMapping(path = "/create")
	public ResponseEntity<RestResponse> createAMessage(@RequestBody MessageDetailsRequest messageDetailsRequest) {
		
		return messageService.createAMessage(messageDetailsRequest.getMessageId(), messageDetailsRequest.getSenderId(),
				messageDetailsRequest.getReceiverId(), messageDetailsRequest.getText(), messageDetailsRequest.getTimeStamp(),
				messageDetailsRequest.getTimeZone(), messageDetailsRequest.getEpochSeconds(), messageDetailsRequest.getDate());
	}
	
	@GetMapping(path = "read/userMessages/{senderId}/{receiverId}")
	public ResponseEntity<RestResponse> findMessagesOfUsers(@PathVariable(value = "senderId") String senderId,
			@PathVariable(value = "receiverId") String receiverId) {
		
		return messageService.readMessagesOfUser(senderId, receiverId);
	}

	@GetMapping(path = "show/conversationscreen/{senderId}")
	public ResponseEntity<RestResponse> showConversationScreenOfUser(@PathVariable(value = "senderId") String senderId) {
		
		return messageService.showConversationScreenOfUser(senderId);
	}
	
}
