package com.java.jkpot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exam_conductors")
public class ExamConductor {

	@Id
	private int examConductorId;
	private String name;
	private String logo;

	public int getExamConductorId() {
		return examConductorId;
	}

	public void setExamConductorId(int examConductorId) {
		this.examConductorId = examConductorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "ExamConductor [examConductorId=" + examConductorId + ", name=" + name + ", logo=" + logo + "]";
	}
}