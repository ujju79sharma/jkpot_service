package com.java.jkpot.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sectional_mocks")
public class SectionalMocks {

	@Id
	private int sectionalMockId;
	private int sectionalId;
	private int subSectionalId;
	private int examId;
	private String sectionName;
	private int sectionQuestionNo;
	private String question;
	private String answer;
	private List<String> options;
	private boolean imageAdded;
	private String imageName;

	public int getSectionalMockId() {
		return sectionalMockId;
	}

	public void setSectionalMockId(int sectionalMockId) {
		this.sectionalMockId = sectionalMockId;
	}

	public int getSectionalId() {
		return sectionalId;
	}

	public void setSectionalId(int sectionalId) {
		this.sectionalId = sectionalId;
	}

	public int getSubSectionalId() {
		return subSectionalId;
	}

	public void setSubSectionalId(int subSectionalId) {
		this.subSectionalId = subSectionalId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public int getSectionQuestionNo() {
		return sectionQuestionNo;
	}

	public void setSectionQuestionNo(int sectionQuestionNo) {
		this.sectionQuestionNo = sectionQuestionNo;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public boolean isImageAdded() {
		return imageAdded;
	}

	public void setImageAdded(boolean imageAdded) {
		this.imageAdded = imageAdded;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public String toString() {
		return "SectionalMocks [sectionalMockId=" + sectionalMockId + ", examId=" + examId + ", sectionName="
				+ sectionName + ", sectionQuestionNo=" + sectionQuestionNo + ", question=" + question + ", answer="
				+ answer + ", options=" + options + ", imageAdded=" + imageAdded + ", imageName=" + imageName + "]";
	}
}
