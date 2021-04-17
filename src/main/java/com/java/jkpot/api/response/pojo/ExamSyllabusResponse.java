package com.java.jkpot.api.response.pojo;

public class ExamSyllabusResponse {

	private int topicId;
	private String topicName;
	
	public ExamSyllabusResponse(int topicId, String topicName) {
		super();
		this.topicId = topicId;
		this.topicName = topicName;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
}
