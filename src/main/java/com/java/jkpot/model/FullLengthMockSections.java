package com.java.jkpot.model;

import java.util.TreeMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "full_length_mock_sections")
public class FullLengthMockSections {

	@Id
	private int id;
	private int examId;
	private String mockName;
	private String examName;
	private TreeMap<String, Integer> sectionInfo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getMockName() {
		return mockName;
	}

	public void setMockName(String mockName) {
		this.mockName = mockName;
	}

	public TreeMap<String, Integer> getSectionInformation() {
		return sectionInfo;
	}

	public void setSectionInformation(TreeMap<String, Integer> sectionInfo) {
		this.sectionInfo = sectionInfo;
	}

	@Override
	public String toString() {
		return "FullLengthMockSections [id=" + id + ", examId=" + examId + ", examName=" + examName + ", sectionInfo="
				+ sectionInfo+ "]";
	}
}