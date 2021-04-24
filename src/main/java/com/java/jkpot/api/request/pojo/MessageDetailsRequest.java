package com.java.jkpot.api.request.pojo;

public class MessageDetailsRequest {

	private int messageId;
	private String senderId;
	private String receiverId;
	private String text;
	private String timeZone;
	private long epochSeconds;
	private String timeStamp;
	private String date;
	
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

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}