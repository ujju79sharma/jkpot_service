package com.java.jkpot.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.poiji.annotation.ExcelCellName;

@Document(collection = "full_length_mocks")
public class FullLengthMocks {

	@Id
	private int fullLengthMockId;
	private int mockId;
	private int examId;
	private String examName;
	private String fullLengthMockName;
	@ExcelCellName("Question No")
	private int questionNo;
	@ExcelCellName("Section-No")
	private int sectionNo;
	@ExcelCellName("Section")
	private String sectionName;
	@ExcelCellName("Section-Question-No")
	private int sectionQuestionNo;
	@ExcelCellName("Questions")
	private String question;
	private List<String> options = new ArrayList<>();
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
	@ExcelCellName("Answer")
	private String answer;
	@ExcelCellName("Image")
	private String imageName;

	public int getFullLengthMockId() {
		return fullLengthMockId;
	}

	public void setFullLengthMockId(int fullLengthMockId) {
		this.fullLengthMockId = fullLengthMockId;
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

	public int getMockId() {
		return mockId;
	}

	public void setMockId(int mockId) {
		this.mockId = mockId;
	}

	public String getFullLengthMockName() {
		return fullLengthMockName;
	}

	public void setFullLengthMockName(String fullLengthMockName) {
		this.fullLengthMockName = fullLengthMockName;
	}

	public int getQuestionNo() {
		return questionNo;
	}

	public void setQuestionNo(int questionNo) {
		this.questionNo = questionNo;
	}

	public int getSectionNo() {
		return sectionNo;
	}

	public void setSectionNo(int sectionNo) {
		this.sectionNo = sectionNo;
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

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
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

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public String toString() {
		return "FullLengthMocks [fullLengthMockId=" + fullLengthMockId + ", examId=" + examId + ", examName=" + examName
				+ ", fullLengthMockName=" + fullLengthMockName + ", questionNo=" + questionNo + ", sectionNo="
				+ sectionNo + ", sectionName=" + sectionName + ", sectionQuestionNo=" + sectionQuestionNo
				+ ", question=" + question + ", options=" + options + ", optionA=" + optionA + ", optionB=" + optionB
				+ ", optionC=" + optionC + ", optionD=" + optionD + ", answer=" + answer + ", imageName=" + imageName
				+ "]";
	}

	
}