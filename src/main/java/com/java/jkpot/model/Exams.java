package com.java.jkpot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exams")
public class Exams {

	@Id
	private int examId;
	private int examConductorId;
	private String examName;
	private String examLogo;

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getExamConductorId() {
		return examConductorId;
	}

	public void setExamConductorId(int examConductorId) {
		this.examConductorId = examConductorId;
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
		return "Exams [examId=" + examId + ", examConductorId=" + examConductorId + ", examName=" + examName
				+ ", examLogo=" + examLogo + "]";
	}
}
