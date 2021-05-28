package com.java.jkpot.api.response.pojo;

import com.java.jkpot.model.StudentsSectionalMarks;

public class StudentSectionalAnswerResponse {

	private int ranking;
	private int totalStudents;
	private StudentsSectionalMarks studentsSectionalMarks;
	private Object topStudents;

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public StudentsSectionalMarks getStudentsSectionalMarks() {
		return studentsSectionalMarks;
	}

	public void setStudentsSectionalMarks(StudentsSectionalMarks studentsSectionalMarks) {
		this.studentsSectionalMarks = studentsSectionalMarks;
	}

	public Object getTopStudents() {
		return topStudents;
	}

	public void setTopStudents(Object topStudents) {
		this.topStudents = topStudents;
	}

	public int getTotalStudents() {
		return totalStudents;
	}

	public void setTotalStudents(int totalStudents) {
		this.totalStudents = totalStudents;
	}
}
