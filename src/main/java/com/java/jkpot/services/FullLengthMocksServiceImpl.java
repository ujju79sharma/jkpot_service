package com.java.jkpot.services;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.jkpot.api.request.pojo.StudentAnswersRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.dao.CountersDAO;
import com.java.jkpot.dao.FullLengthMockDAO;
import com.java.jkpot.dao.FullLengthSectionsDAO;
import com.java.jkpot.dao.UsersDAO;
import com.java.jkpot.model.FullLengthMockSections;
import com.java.jkpot.model.FullLengthMocks;
import com.java.jkpot.model.StudentsFullLengthMockMarks;
import com.java.jkpot.model.Users;

@Service
public class FullLengthMocksServiceImpl implements FullLengthMocksService{

	@Autowired
	private FullLengthSectionsDAO fullLengthSectionsDAO;
	@Autowired
	private FullLengthMockDAO fullLengthMockDAO;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private UsersDAO usersDAO;
	@Autowired
	private CountersDAO sequence;
	
	@Override
	public ResponseEntity<RestResponse> fetchSectionInfoByExamId(int examId) {
		
		List<FullLengthMockSections> fullLengthMockSections = fullLengthSectionsDAO.findByExamId(examId);
		
		if (fullLengthMockSections.size() > 0) {

			RestResponse response = new RestResponse("SUCCESS", fullLengthMockSections, 200);

			return ResponseEntity.ok(response);
		}else {

			RestResponse response = new RestResponse("FAILURE", "data not found", 404);

			return ResponseEntity.status(404).body(response);
		}
	}
	
	@Override
	public ResponseEntity<RestResponse> findSectionalMockByExamIdAndMockId(int examId, int mockId) {

		List<FullLengthMocks> fullLengthMocks =  fullLengthMockDAO.findByExamIdAndFullLengthMockId(examId, mockId);

		if (fullLengthMocks.size() > 0) {

			RestResponse response = new RestResponse("SUCCESS", fullLengthMocks, 200);

			return ResponseEntity.ok(response);
		}else {

			RestResponse response = new RestResponse("FAILURE", "data not found", 404);

			return ResponseEntity.status(404).body(response);
		}
	}

	@Override
	public ResponseEntity<RestResponse> uploadAndFindStudentMarks(StudentAnswersRequest studentAnswersRequest) {

		List<FullLengthMocks> fullLengthMocks =  fullLengthMockDAO.findByExamIdAndFullLengthMockId(studentAnswersRequest.getExamId(), 
				studentAnswersRequest.getFullLengthMockId());

		if (fullLengthMocks.size() > 0 && studentAnswersRequest.getUserId() != null && fullLengthMocks.size() == studentAnswersRequest.getAnswers().size()) {

			double correctAnswer = 0;
			double incorrectAnswer = 0;
			int skippedQuestion = 0;
			List<Integer> correctAnswers = new LinkedList<Integer>();
			List<Integer> incorrectAnswers = new LinkedList<Integer>();

			for (int i = 0; i <fullLengthMocks.size(); i++) {

				if (studentAnswersRequest.getAnswers().get(i) != null && studentAnswersRequest.getAnswers().get(i).length() > 0 && 
						fullLengthMocks.get(i).getAnswer().equalsIgnoreCase(studentAnswersRequest.getAnswers().get(i))) {
					correctAnswer = correctAnswer+1;
					correctAnswers.add(fullLengthMocks.get(i).getSectionQuestionNo());
				}else if (studentAnswersRequest.getAnswers().get(i) != null && studentAnswersRequest.getAnswers().get(i).length() > 0 && 
				!fullLengthMocks.get(i).getAnswer().equalsIgnoreCase(studentAnswersRequest.getAnswers().get(i))) {
					incorrectAnswer = incorrectAnswer+1;
					incorrectAnswers.add(fullLengthMocks.get(i).getSectionQuestionNo());
				}else {
					skippedQuestion += 1;
				}
			}

			double totalMarks = correctAnswer-incorrectAnswer*0.25;

			StudentsFullLengthMockMarks mockMarks = new StudentsFullLengthMockMarks();

			mockMarks.setCorrectAnswers(correctAnswer);
			mockMarks.setIncorrectAnswers(incorrectAnswer);
			mockMarks.setCorrectQuestions(correctAnswers);
			mockMarks.setIncorrectQuestions(incorrectAnswers);
			mockMarks.setSkippedQuestion(skippedQuestion);
			mockMarks.setTotalMarks(totalMarks);
			mockMarks.setExamId(studentAnswersRequest.getExamId());
			mockMarks.setMockId(studentAnswersRequest.getFullLengthMockId());
			mockMarks.setUserId(studentAnswersRequest.getUserId());
			mockMarks.setMockRecordedDate(LocalDate.now());
			mockMarks.setMockName(fullLengthMocks.get(0).getFullLengthMockName());
			mockMarks.setExamName(fullLengthMocks.get(0).getFullLengthMockName());

			Users user = usersDAO.getUsersFirstNameAndLastName(studentAnswersRequest.getUserId());

			if (user != null)
				mockMarks.setUserName(user.getFirstName()+" "+ user.getLastName());
			else // if no user found
				mockMarks.setUserName("User_"+ String.format("%06d", new Random().nextInt()));

			//remove older record
			if (user != null)
				mongoTemplate.remove(Query.query(Criteria.where("userId").is(user.getUserId()))
						.addCriteria(Criteria.where("mockId").is(studentAnswersRequest.getFullLengthMockId()))
						.addCriteria(Criteria.where("totalMarks").lte(totalMarks))
						.addCriteria(Criteria.where("examId").is(studentAnswersRequest.getExamId())), StudentsFullLengthMockMarks.class);

			mockMarks.setStudentFullLengthMockMarksId(sequence.getNextSequenceOfField("studentFullLengthMockMarksId"));
			mongoTemplate.save(mockMarks, "students_full_length_mock_marks");

			RestResponse response = new RestResponse("SUCCESS", mockMarks, 200);
			return ResponseEntity.ok(response);
		}else {
			RestResponse response = new RestResponse("FAILURE", "Sectional Mock not exists", 404);
			return ResponseEntity.status(404).body(response);
		}
	}
}