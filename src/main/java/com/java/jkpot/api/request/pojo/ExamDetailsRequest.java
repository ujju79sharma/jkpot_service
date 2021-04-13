package com.java.jkpot.api.request.pojo;

public class ExamDetailsRequest {

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
}
