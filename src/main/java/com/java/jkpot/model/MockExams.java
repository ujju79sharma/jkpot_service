package com.java.jkpot.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.poiji.annotation.ExcelCellName;

@Document(collection = "mock_exams")
public class MockExams {

	@Id
	private int mockExamId;
	private int examId;
	@ExcelCellName("Section") 
	private String sectionName;
	@ExcelCellName("Sno") 
	private int sectionQuestionNo;
	@ExcelCellName("Questions")
	private String question;
	@ExcelCellName("Answer")
	private String answer;
	
	@Transient
	@ExcelCellName("Option-A")
	private String optionA;
	@Transient
	@ExcelCellName("Option-B")
	private String optionB;
	@Transient
	@ExcelCellName("Option-C")
	private String optionC;
	@Transient
	@ExcelCellName("Option-D")
	private String optionD;
	
	@ExcelCellName("Name")
	private List<String> options;
	@ExcelCellName("Image-Added")
	private String imageOption;
	
	private boolean imageAdded;
	@ExcelCellName("Image")
	private String imageName;

	public int getMockExamId() {
		return mockExamId;
	}

	public void setMockExamId(int mockExamId) {
		this.mockExamId = mockExamId;
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

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public String getImageOption() {
		return imageOption;
	}

	public void setImageOption(String imageOption) {
		this.imageOption = imageOption;
	}

	@Override
	public String toString() {
		return "MockExams [mockExamId=" + mockExamId + ", examId=" + examId + ", sectionName=" + sectionName
				+ ", sectionQuestionNo=" + sectionQuestionNo + ", question=" + question + ", answer=" + answer
				+ ", optionA=" + optionA + ", optionB=" + optionB + ", optionC=" + optionC + ", optionD=" + optionD
				+ ", options=" + options + ", imageAdded=" + imageAdded + ", imageName=" + imageName + "]";
	}
}
