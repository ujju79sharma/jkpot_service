package com.java.jkpot.model;

import java.time.LocalDate;
import java.util.TreeMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students_full_length_mock_marks")
public class StudentsFullLengthMockMarks {

	@Id
	private int studentFullLengthMockMarksId;
	private int mockId;
	private String mockName;
	private int examId;
	private String examName;
	private String userId;
	private String userName;
	private double totalMarks;
	private double correctAnswers;
	private int skippedQuestion;
	private double incorrectAnswers;
	private TreeMap<String, Integer> attemptedQuestions;
	private TreeMap<String, Integer> correctQuestions;
	private TreeMap<String, Integer> incorrectQuestions;
	private LocalDate mockRecordedDate;

	public int getStudentFullLengthMockMarksId() {
		return studentFullLengthMockMarksId;
	}

	public void setStudentFullLengthMockMarksId(int studentFullLengthMockMarksId) {
		this.studentFullLengthMockMarksId = studentFullLengthMockMarksId;
	}

	public int getMockId() {
		return mockId;
	}

	public void setMockId(int mockId) {
		this.mockId = mockId;
	}

	public String getMockName() {
		return mockName;
	}

	public void setMockName(String mockName) {
		this.mockName = mockName;
	}

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

	public int getSkippedQuestion() {
		return skippedQuestion;
	}

	public void setSkippedQuestion(int skippedQuestion) {
		this.skippedQuestion = skippedQuestion;
	}

	public double getIncorrectAnswers() {
		return incorrectAnswers;
	}

	public void setIncorrectAnswers(double incorrectAnswers) {
		this.incorrectAnswers = incorrectAnswers;
	}

	public TreeMap<String, Integer> getCorrectQuestions() {
		return correctQuestions;
	}

	public void setCorrectQuestions(TreeMap<String, Integer> correctQuestions) {
		this.correctQuestions = correctQuestions;
	}

	public TreeMap<String, Integer> getIncorrectQuestions() {
		return incorrectQuestions;
	}

	public void setIncorrectQuestions(TreeMap<String, Integer> incorrectQuestions) {
		this.incorrectQuestions = incorrectQuestions;
	}

	public TreeMap<String, Integer> getAttemptedQuestions() {
		return attemptedQuestions;
	}

	public void setAttemptedQuestions(TreeMap<String, Integer> attemptedQuestions) {
		this.attemptedQuestions = attemptedQuestions;
	}

	public LocalDate getMockRecordedDate() {
		return mockRecordedDate;
	}

	public void setMockRecordedDate(LocalDate mockRecordedDate) {
		this.mockRecordedDate = mockRecordedDate;
	}

	@Override
	public String toString() {
		return "StudentsFullLengthMockMarks [studentFullLengthMockMarksId=" + studentFullLengthMockMarksId + ", mockId="
				+ mockId + ", mockName=" + mockName + ", examId=" + examId + ", examName=" + examName + ", userId="
				+ userId + ", userName=" + userName + ", totalMarks=" + totalMarks + ", correctAnswers="
				+ correctAnswers + ", skippedQuestion=" + skippedQuestion + ", incorrectAnswers=" + incorrectAnswers
				+ ", correctQuestions=" + correctQuestions + ", incorrectQuestions=" + incorrectQuestions
				+ ", mockRecordedDate=" + mockRecordedDate + "]";
	}
}