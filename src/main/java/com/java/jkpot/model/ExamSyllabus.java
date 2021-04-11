package com.java.jkpot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exam_syllabus")
public class ExamSyllabus {

	@Id
	private int examSyllabusId;
	private int examId;
	private int topicId;
	private String topic;
	private int subTopicId;
	private String subTopic;
	private int numberOfQuestions;

	public int getExamSyllabusId() {
		return examSyllabusId;
	}

	public void setExamSyllabusId(int examSyllabusId) {
		this.examSyllabusId = examSyllabusId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getSubTopicId() {
		return subTopicId;
	}

	public void setSubTopicId(int subTopicId) {
		this.subTopicId = subTopicId;
	}

	public String getSubTopic() {
		return subTopic;
	}

	public void setSubTopic(String subTopic) {
		this.subTopic = subTopic;
	}

	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	@Override
	public String toString() {
		return "ExamSyllabus [examSyllabusId=" + examSyllabusId + ", examId=" + examId + ", topicId=" + topicId
				+ ", topic=" + topic + ", subTopicId=" + subTopicId + ", subTopic=" + subTopic + ", numberOfQuestions="
				+ numberOfQuestions + "]";
	}
}
