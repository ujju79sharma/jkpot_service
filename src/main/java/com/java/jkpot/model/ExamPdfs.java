package com.java.jkpot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exam_pdfs")
public class ExamPdfs {

	@Id
	private int examPdfId;
	private int examId;
	private String pdfName;
	private String pdfUrl;

	public int getExamPdfId() {
		return examPdfId;
	}

	public void setExamPdfId(int examPdfId) {
		this.examPdfId = examPdfId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public String getPdfName() {
		return pdfName;
	}

	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}

	public String getPdfUrl() {
		return pdfUrl;
	}

	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

	@Override
	public String toString() {
		return "ExamPdfs [examPdfId=" + examPdfId + ", pdfName=" + pdfName + ", pdfUrl=" + pdfUrl + "]";
	}
}