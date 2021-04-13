package com.java.jkpot.api.request.pojo;

public class ExamPdfDetailsRequest {

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
}
