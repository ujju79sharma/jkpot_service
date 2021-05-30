package com.java.jkpot.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.jkpot.api.request.pojo.StudentAnswersRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.api.response.pojo.StudentFullMockAnswerResponse;
import com.java.jkpot.dao.CountersDAO;
import com.java.jkpot.dao.FullLengthMockDAO;
import com.java.jkpot.dao.FullLengthSectionsDAO;
import com.java.jkpot.dao.StudentsFullLengthMarksDAO;
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
	@Autowired
	private StudentsFullLengthMarksDAO studentsFullLengthMarksDAO;

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

			StudentsFullLengthMockMarks checkIfStudentHasGivenTheMock = studentsFullLengthMarksDAO.checkIfUserHasGivenTheMock(studentAnswersRequest.getExamId(), 
					studentAnswersRequest.getFullLengthMockId(), studentAnswersRequest.getUserId());

			if (checkIfStudentHasGivenTheMock == null) {
				double correctAnswer = 0;
				double incorrectAnswer = 0;
				int skippedQuestion = 0;
	
				TreeMap<String, Integer> attemptedQuestions = new TreeMap<>();
				TreeMap<String, Integer> correctAnswers = new TreeMap<>();
				TreeMap<String, Integer> incorrectAnswers = new TreeMap<>();
				int attemptCount = 0;
				int correctCount = 0;
				int incorrectCount = 0;
	
				for (int i = 0; i <fullLengthMocks.size(); i++) {
					
					if (studentAnswersRequest.getAnswers().get(i) != null && studentAnswersRequest.getAnswers().get(i).length() > 0 && 
							fullLengthMocks.get(i).getAnswer().equalsIgnoreCase(studentAnswersRequest.getAnswers().get(i))) {
						
						correctAnswer = correctAnswer+1;
	
						if (!attemptedQuestions.keySet().contains(fullLengthMocks.get(i).getSectionName())) {
	
							attemptCount = 0;
							correctCount = 0;
	
							correctAnswers.put(fullLengthMocks.get(i).getSectionName(), ++correctCount);
							attemptedQuestions.put(fullLengthMocks.get(i).getSectionName(), ++attemptCount);
						}else {
							correctAnswers.put(fullLengthMocks.get(i).getSectionName(), ++correctCount);
							attemptedQuestions.put(fullLengthMocks.get(i).getSectionName(), ++attemptCount);
						}
	
					}else if (studentAnswersRequest.getAnswers().get(i) != null && studentAnswersRequest.getAnswers().get(i).length() > 0 && 
							!fullLengthMocks.get(i).getAnswer().equalsIgnoreCase(studentAnswersRequest.getAnswers().get(i))) {
						
						incorrectAnswer = incorrectAnswer+1;
						incorrectAnswers.put(fullLengthMocks.get(i).getSectionName(), fullLengthMocks.get(i).getQuestionNo());
						
						if (!attemptedQuestions.keySet().contains(fullLengthMocks.get(i).getSectionName())) {
							attemptCount = 0;
							incorrectCount = 0;
	
							attemptedQuestions.put(fullLengthMocks.get(i).getSectionName(), ++attemptCount);
							incorrectAnswers.put(fullLengthMocks.get(i).getSectionName(), ++incorrectCount);
						}else {
							attemptedQuestions.put(fullLengthMocks.get(i).getSectionName(), ++attemptCount);
							incorrectAnswers.put(fullLengthMocks.get(i).getSectionName(), ++incorrectCount);
						}
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
				mockMarks.setAttemptedQuestions(attemptedQuestions);
				mockMarks.setSkippedQuestion(skippedQuestion);
				mockMarks.setTotalMarks(totalMarks);
				mockMarks.setExamId(studentAnswersRequest.getExamId());
				mockMarks.setMockId(studentAnswersRequest.getFullLengthMockId());
				mockMarks.setUserId(studentAnswersRequest.getUserId());
				mockMarks.setMockRecordedDate(LocalDate.now());
				mockMarks.setMockName(fullLengthMocks.get(0).getFullLengthMockName());
				mockMarks.setExamName(fullLengthMocks.get(0).getExamName());
	
				Users user = usersDAO.getUsersFirstNameAndLastName(studentAnswersRequest.getUserId());
	
				if (user != null)
					mockMarks.setUserName(user.getFirstName()+" "+ user.getLastName());
				else // if no user found
					mockMarks.setUserName("User_"+ String.format("%06d", new Random().nextInt()));

				mockMarks.setStudentFullLengthMockMarksId(sequence.getNextSequenceOfField("studentFullLengthMockMarksId"));
				mockMarks.setStatus("taken");

				mongoTemplate.save(mockMarks, "students_full_length_mock_marks");

				List<Document> studentList = studentsFullLengthMarksDAO.fetchHighestMarksOfStudents(studentAnswersRequest.getExamId(), studentAnswersRequest.getFullLengthMockId());

				Map<String, Object> obj = new HashMap<String, Object>();

				double marks = mockMarks.getTotalMarks();
				String marksInString = String.valueOf(marks);

				if (marksInString.contains(".0"))
					obj.put("totalMarks", (int)marks);
				else
					obj.put("totalMarks", marks);

				obj.put("_id", mockMarks.getUserId());
				obj.put("userName", mockMarks.getUserName());
				obj.put("mock", mockMarks.getMockName());
				obj.put("exam", mockMarks.getExamName());
	
				int rank = studentList.indexOf(new Document(obj))+1;
	
				StudentFullMockAnswerResponse finalResponse = new StudentFullMockAnswerResponse();
	
				finalResponse.setStudentsFullLengthMockMarks(mockMarks);
				finalResponse.setRanking(rank);
				finalResponse.setTotalStudents(studentList.size());
				finalResponse.setTopStudents(this.fetchTopStudentsInAMock(studentAnswersRequest.getExamId(), studentAnswersRequest.getFullLengthMockId(),
					studentAnswersRequest.getUserId()).getData());
	
				RestResponse response = new RestResponse("SUCCESS", finalResponse, 200);
				return ResponseEntity.ok(response);
			}else {
				
				List<Document> studentList = studentsFullLengthMarksDAO.fetchHighestMarksOfStudents(studentAnswersRequest.getExamId(), studentAnswersRequest.getFullLengthMockId());

				Map<String, Object> obj = new HashMap<String, Object>();

				double marks = checkIfStudentHasGivenTheMock.getTotalMarks();
				String marksInString = String.valueOf(marks);

				if (marksInString.contains(".0"))
					obj.put("totalMarks", (int)marks);
				else
					obj.put("totalMarks", marks);

				obj.put("_id", checkIfStudentHasGivenTheMock.getUserId());
				
				obj.put("userName", checkIfStudentHasGivenTheMock.getUserName());
				obj.put("mock", checkIfStudentHasGivenTheMock.getMockName());
				obj.put("exam", checkIfStudentHasGivenTheMock.getExamName());

				int rank = studentList.indexOf(new Document(obj))+1;

				StudentFullMockAnswerResponse finalResponse = new StudentFullMockAnswerResponse();

				finalResponse.setStudentsFullLengthMockMarks(checkIfStudentHasGivenTheMock);
				finalResponse.setRanking(rank);
				finalResponse.setTotalStudents(studentList.size());
				finalResponse.setTopStudents(this.fetchTopStudentsInAMock(studentAnswersRequest.getExamId(), studentAnswersRequest.getFullLengthMockId(),
					studentAnswersRequest.getUserId()).getData());

				RestResponse response = new RestResponse("SUCCESS", finalResponse, 200);
				return ResponseEntity.status(200).body(response);
			}
		}else {
			RestResponse response = new RestResponse("FAILURE", "Sectional Mock not exists", 404);
			return ResponseEntity.status(404).body(response);
		}
	}

	@Override
	public RestResponse fetchTopStudentsInAMock(int examId, int mockId, String userId) {

		if (examId > 0 && mockId > 0 && userId != null) {
			
			List<Document> students = studentsFullLengthMarksDAO.findByExamIdAndMockId(examId, mockId);

			if (students != null && students.size() > 0) {

				if (students.size() == 10) {

					if (students.stream().map(e->e.getString("userId")).collect(Collectors.toList()).contains(userId)) { // if userId exist in top 10 list

						RestResponse response = new RestResponse("SUCCESS", students, 200);

						return response;
					}else {
						
						Document userMarks = studentsFullLengthMarksDAO.findTopMarksOfStudent(examId, mockId, userId);

						if (userMarks != null)
							students.add(userMarks);

						RestResponse response = new RestResponse("SUCCESS", students, 200);

						return response;
					}
				}else {
					RestResponse response = new RestResponse("SUCCESS", students, 200);

					return response;
				}
			}else {
				RestResponse response = new RestResponse("SUCCESS", "No mock given by the user.", 204);

				return response;
			}
		}else {
			
			RestResponse response = new RestResponse("FAILURE", "please insert userId or sectionalId or subSectionalId", 400);
			
			return response;
		}
	}

	public ResponseEntity<RestResponse> fetchTopStudentsInAllMocks() {
		
		List<Document> finalResponse = fullLengthMockDAO.findTopStudentsInMock();

		if (finalResponse.size() > 0) {
			RestResponse response = new RestResponse("SUCCESS", finalResponse, 200);
	
			return ResponseEntity.ok(response);
		}else {
			RestResponse response = new RestResponse("SUCCESS", "No full-length-mock given by students.", 200);

			return ResponseEntity.ok(response);
		}
	}
}