package com.java.jkpot.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exams")
public class Exams {

	private int examId;
	private String examName;
	private String examLogo;

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getExamLogo() {
		return examLogo;
	}

	public void setExamLogo(String examLogo) {
		this.examLogo = examLogo;
	}

	@Override
	public String toString() {
		return "Exams [examId=" + examId + ", examName=" + examName + ", examLogo=" + examLogo + "]";
	}
}
