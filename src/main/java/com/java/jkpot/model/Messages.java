package com.java.jkpot.model;

import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
public class Messages {

	@Id
	private int messageId;
	private String senderId;
	private String senderName;
	private String receiverId;
	private String receiverName;
	private String text;
	private String timeZone;
	private long epochSeconds;
	private LocalTime timeStamp;
	private String date;
	private boolean isSeen;

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public long getEpochSeconds() {
		return epochSeconds;
	}

	public void setEpochSeconds(long epochSeconds) {
		this.epochSeconds = epochSeconds;
	}

	public LocalTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isSeen() {
		return isSeen;
	}

	public void setSeen(boolean isSeen) {
		this.isSeen = isSeen;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	@Override
	public String toString() {
		return "Messages [messageId=" + messageId + ", senderId=" + senderId + ", receiverId=" + receiverId + ", text="
				+ text + ", timeZone=" + timeZone + ", epochSeconds=" + epochSeconds + ", timeStamp=" + timeStamp
				+ ", date=" + date + ", isSeen=" + isSeen + "]";
	}
}