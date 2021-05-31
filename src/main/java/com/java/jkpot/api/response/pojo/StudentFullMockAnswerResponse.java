package com.java.jkpot.api.response.pojo;

import com.java.jkpot.model.StudentsFullLengthMockMarks;

public class StudentFullMockAnswerResponse {

	private int ranking;
	private int totalStudents;
	private StudentsFullLengthMockMarks studentsFullLengthMockMarks;
	private Object topStudents;
	private Object studentsMockAnalysis;

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public int getTotalStudents() {
		return totalStudents;
	}

	public void setTotalStudents(int totalStudents) {
		this.totalStudents = totalStudents;
	}

	public StudentsFullLengthMockMarks getStudentsFullLengthMockMarks() {
		return studentsFullLengthMockMarks;
	}

	public void setStudentsFullLengthMockMarks(StudentsFullLengthMockMarks studentsFullLengthMockMarks) {
		this.studentsFullLengthMockMarks = studentsFullLengthMockMarks;
	}

	public Object getTopStudents() {
		return topStudents;
	}

	public void setTopStudents(Object topStudents) {
		this.topStudents = topStudents;
	}

	public Object getStudentsMockAnalysis() {
		return studentsMockAnalysis;
	}

	public void setStudentsMockAnalysis(Object studentsMockAnalysis) {
		this.studentsMockAnalysis = studentsMockAnalysis;
	}
}