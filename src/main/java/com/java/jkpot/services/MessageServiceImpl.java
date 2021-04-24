package com.java.jkpot.services;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.dao.CountersDAO;
import com.java.jkpot.model.Messages;
import com.java.jkpot.model.Users;
import com.java.jkpot.utils.GoogleFirebaseUtilityDAO;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CountersDAO sequence;
	@Autowired
	private GoogleFirebaseUtilityDAO pushNotificationDAO;
	
	@Override
	public ResponseEntity<RestResponse> createAMessage(int messageId, String senderId, String receiverId, String text,
			String timeStamp, String timeZone, long epochSeconds, String date) {
		
		if (messageId == 0) {
			
			Users receiverInfo = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(receiverId)), Users.class);
			Users senderInfo = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(senderId)), Users.class);
			
			if (receiverInfo != null && senderInfo != null) {

				Messages message = new Messages();
	
				message.setText(text);
				message.setSenderName(senderId);
				message.setSenderId(senderId);
				message.setSenderName(senderInfo.getFirstName()+" "+senderInfo.getLastName());
				message.setReceiverId(receiverId);
				message.setReceiverName(receiverInfo.getFirstName()+" "+receiverInfo.getLastName());
				message.setTimeZone(timeZone);
				message.setTimeStamp(LocalTime.parse(timeStamp));
				message.setEpochSeconds(epochSeconds);
				message.setDate(date);
				
				if (text != null && senderId !=null && receiverId != null && timeStamp != null) {
					
					String notificationHeader = senderInfo.getFirstName()+" has messaged you"; 
					String deepLink = "1";

					pushNotificationDAO.sendNotificationToUser(senderInfo.getFirstName(), senderId, receiverInfo.getFcmToken(), notificationHeader, text, deepLink);

					message.setMessageId(sequence.getNextSequenceOfField("messageId"));
					mongoTemplate.save(message, "messages");

					RestResponse response = new RestResponse("SUCCESS", message, 200);

					return ResponseEntity.ok(response);
				}
				else {
					RestResponse response = new RestResponse("FAILURE", "missed to add text/senderId/receiverId/timeStamp ", 404);
					
					return ResponseEntity.status(404).body(response);
				}
			} else {
				
				RestResponse response = new RestResponse("FAILURE", "users not exist", 404);
				
				return ResponseEntity.status(404).body(response);
			}
		}else if (messageId > 0) {
			
			Messages foundMessage = mongoTemplate.findOne(Query.query(Criteria.where("messageId").is(messageId)), Messages.class);
			
			String actualSenderId = foundMessage.getSenderId();
			String actualReceiverId = foundMessage.getReceiverId();
			
			Query query = new Query();
			
			query.addCriteria(Criteria.where("senderId").is(actualSenderId)).addCriteria(Criteria.where("receiverId").is(actualReceiverId));
			Update update = new Update().set("isSeen", true);
			mongoTemplate.updateMulti(query, update, Messages.class);
			
			RestResponse response = new RestResponse("SUCCESS", foundMessage, 200);
			
			return ResponseEntity.ok(response);
		}else {
			RestResponse response = new RestResponse("FAILURE", "Nothing to update", 404);
		
			return ResponseEntity.ok(response);
		}
	}

	@Override
	public ResponseEntity<RestResponse> readMessagesOfUser(String senderId, String receiverId, int limit) {
		
		Users receiverInfo = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(receiverId)), Users.class);
		Users senderInfo = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(senderId)), Users.class);

		if (receiverInfo != null && senderInfo != null) {
			Query query = new Query();
			query.addCriteria(Criteria.where("receiverId").in(receiverId, senderId));
			query.addCriteria(Criteria.where("senderId").in(senderId, receiverId));
			query.with(Sort.by(Sort.Direction.DESC, "messageId"));
			query.limit(limit);
			List<Messages> messageList = mongoTemplate.find(query, Messages.class);
			
			RestResponse response = new RestResponse("SUCCESS", messageList, 200);

			return ResponseEntity.ok(response);

		}else {
			RestResponse response = new RestResponse("FAILURE", "users not found", 404);
		
			return ResponseEntity.status(404).body(response);
		}
	}

	@Override
	public ResponseEntity<RestResponse> showConversationScreenOfUser(String senderId) {
		
		Users senderInfo = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(senderId)), Users.class);

		if (senderInfo != null) {
			Query query = new Query();
			Criteria criteria1 = Criteria.where("receiverId").is(senderId);
			Criteria criteria2 = Criteria.where("senderId").is(senderId);
			Criteria criteria3 = Criteria.where("isSeen").is(false);
			query.limit(1);
			query.with(Sort.by(Sort.Direction.DESC, "messageId"));
			List<Messages> messageList = mongoTemplate.find(query.addCriteria(criteria3.orOperator(criteria2,criteria1)), Messages.class);
			
			RestResponse response = new RestResponse("SUCCESS", messageList, 200);

			return ResponseEntity.ok(response);

		}else {
			RestResponse response = new RestResponse("FAILURE", "users not found", 404);
		
			return ResponseEntity.status(404).body(response);
		}
	}
}
