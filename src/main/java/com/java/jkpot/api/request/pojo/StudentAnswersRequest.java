package com.java.jkpot.api.request.pojo;

import java.util.List;

public class StudentAnswersRequest {

	private String userId;
	private int examId;
	private int sectionalId;
	private int subSectionId;
	private int fullLengthMockId;
	private List<String> answers;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getSectionalId() {
		return sectionalId;
	}

	public void setSectionalId(int sectionalId) {
		this.sectionalId = sectionalId;
	}

	public int getSubSectionId() {
		return subSectionId;
	}

	public void setSubSectionId(int subSectionId) {
		this.subSectionId = subSectionId;
	}

	public int getFullLengthMockId() {
		return fullLengthMockId;
	}

	public void setFullLengthMockId(int fullLengthMockId) {
		this.fullLengthMockId = fullLengthMockId;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}
}