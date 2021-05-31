package com.java.jkpot.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students_sectional_marks")
public class StudentsSectionalMarks {

	@Id
	private int studentSectionalMarksId;
	private int sectionalId;
	private String sectionalName;
	private int subSectionalId;
	private String subSectionName;
	private int examId;
	private String userId;
	private String userName;
	private double totalMarks;
	private double correctAnswers;
	private int skippedQuestion;
	private double incorrectAnswers;
	private List<Integer> correctQuestions;
	private List<Integer> incorrectQuestions;
	private List<Integer> skippedQuestions;
	private LocalDate mockRecordedDate;
	private String status;
	private List<String> studentsAnswer;

	public int getStudentSectionalMarksId() {
		return studentSectionalMarksId;
	}

	public void setStudentSectionalMarksId(int studentSectionalMarksId) {
		this.studentSectionalMarksId = studentSectionalMarksId;
	}

	public int getSectionalId() {
		return sectionalId;
	}

	public void setSectionalId(int sectionalId) {
		this.sectionalId = sectionalId;
	}

	public String getSectionalName() {
		return sectionalName;
	}

	public void setSectionalName(String sectionalName) {
		this.sectionalName = sectionalName;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getSubSectionalId() {
		return subSectionalId;
	}

	public void setSubSectionalId(int subSectionalId) {
		this.subSectionalId = subSectionalId;
	}

	public String getSubSectionName() {
		return subSectionName;
	}

	public void setSubSectionName(String subSectionName) {
		this.subSectionName = subSectionName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(double totalMarks) {
		this.totalMarks = totalMarks;
	}

	public double getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(double correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public double getIncorrectAnswers() {
		return incorrectAnswers;
	}

	public void setIncorrectAnswers(double incorrectAnswers) {
		this.incorrectAnswers = incorrectAnswers;
	}

	public List<Integer> getCorrectQuestions() {
		return correctQuestions;
	}

	public void setCorrectQuestions(List<Integer> correctQuestions) {
		this.correctQuestions = correctQuestions;
	}

	public List<Integer> getIncorrectQuestions() {
		return incorrectQuestions;
	}

	public int getSkippedQuestion() {
		return skippedQuestion;
	}

	public void setSkippedQuestion(int skippedQuestion) {
		this.skippedQuestion = skippedQuestion;
	}

	public void setIncorrectQuestions(List<Integer> incorrectQuestions) {
		this.incorrectQuestions = incorrectQuestions;
	}

	public List<Integer> getSkippedQuestions() {
		return skippedQuestions;
	}

	public void setSkippedQuestions(List<Integer> skippedQuestions) {
		this.skippedQuestions = skippedQuestions;
	}

	public LocalDate getMockRecordedDate() {
		return mockRecordedDate;
	}

	public void setMockRecordedDate(LocalDate mockRecordedDate) {
		this.mockRecordedDate = mockRecordedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getStudentsAnswer() {
		return studentsAnswer;
	}

	public void setStudentsAnswer(List<String> studentsAnswer) {
		this.studentsAnswer = studentsAnswer;
	}

	@Override
	public String toString() {
		return "StudentsSectionalMarks [studentSectionalMarksId=" + studentSectionalMarksId + ", sectionalId="
				+ sectionalId + ", sectionalName=" + sectionalName + ", subSectionalId=" + subSectionalId
				+ ", subSectionName=" + subSectionName + ", examId=" + examId + ", userId=" + userId + ", userName="
				+ userName + ", totalMarks=" + totalMarks + ", correctAnswers=" + correctAnswers + ", skippedQuestion="
				+ skippedQuestion + ", incorrectAnswers=" + incorrectAnswers + ", correctQuestions=" + correctQuestions
				+ ", incorrectQuestions=" + incorrectQuestions + ", skippedQuestions=" + skippedQuestions + "]";
	}

}
