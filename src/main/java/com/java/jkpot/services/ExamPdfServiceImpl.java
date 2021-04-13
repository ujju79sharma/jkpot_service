package com.java.jkpot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.dao.CountersDAO;
import com.java.jkpot.model.ExamPdfs;

@Service
public class ExamPdfServiceImpl implements ExamPdfService{

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CountersDAO sequence;
	
	@Override
	public ResponseEntity<RestResponse> createExamPdf(int examPdfId, int examId, String pdfName, String pdfUrl) {
		
		if (examPdfId > 0) {
			
			ExamPdfs examPdf = mongoTemplate.findOne(Query.query(Criteria.where("examPdfId").is(examPdfId)), ExamPdfs.class);
			
			if (examPdf != null) {
				
				if (pdfName != null)
					examPdf.setPdfName(pdfName);
				if (pdfUrl != null)
					examPdf.setPdfUrl(pdfUrl);
				
				mongoTemplate.save(examPdf, "exam_pdfs");

				RestResponse response = new RestResponse("SUCCESS", examPdf, 200);
				
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}else {
				RestResponse response = new RestResponse("FAILURE", "examPdfId does not exist", 404);
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
			
		}else if (examId == 0) {
			
			ExamPdfs examPdf = new ExamPdfs();
			
			examPdf.setExamId(examId);
			examPdf.setExamPdfId(sequence.getNextSequenceOfField("examPdfId"));
			examPdf.setPdfName(pdfName);
			examPdf.setPdfUrl(pdfUrl);

			mongoTemplate.save(examPdf, "exam_pdfs");

			RestResponse response = new RestResponse("SUCCESS", examPdf, 200);

			return ResponseEntity.status(HttpStatus.OK).body(response);
		}else
			return null;
	}

	@Override
	public ResponseEntity<RestResponse> readExamPdf(int examPdfId) {

		if (examPdfId > 0) {
			ExamPdfs examPdf = mongoTemplate.findOne(Query.query(Criteria.where("examPdfId").is(examPdfId)), ExamPdfs.class);
			
			if (examPdf != null) {

				RestResponse response = new RestResponse("SUCCESS", examPdf, 200);

				return ResponseEntity.status(HttpStatus.OK).body(response);
			}else {
				RestResponse response = new RestResponse("FAILURE", null, 404);
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		}else {
			RestResponse response = new RestResponse("FAILURE", null, 404);
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	@Override
	public ResponseEntity<RestResponse> readAllExamPdfs(int examId) {
		if (examId > 0) {
			List<ExamPdfs> examPdfs = mongoTemplate.find(Query.query(Criteria.where("examPdfId").is(examId)), ExamPdfs.class);
			
			if (examPdfs != null && examPdfs.size() > 0) {

				RestResponse response = new RestResponse("SUCCESS", examPdfs, 200);

				return ResponseEntity.status(HttpStatus.OK).body(response);
			}else {
				RestResponse response = new RestResponse("FAILURE", null, 404);

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		}else {
			RestResponse response = new RestResponse("FAILURE", null, 404);
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	@Override
	public ResponseEntity<RestResponse> deleteExamPdf(int examPdfId) {

		if (examPdfId > 0) {
			ExamPdfs examPdf = mongoTemplate.findOne(Query.query(Criteria.where("examPdfId").is(examPdfId)), ExamPdfs.class);
			
			if (examPdf != null) {

				mongoTemplate.remove(examPdf, "exam_pdfs");
				
				RestResponse response = new RestResponse("SUCCESS", "Deleted pdf data successfully.", 200);

				return ResponseEntity.status(HttpStatus.OK).body(response);
			}else {
				RestResponse response = new RestResponse("FAILURE", null, 404);
				
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		}else {
			RestResponse response = new RestResponse("FAILURE", null, 404);
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}
}
