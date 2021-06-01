package com.java.jkpot.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.jkpot.api.request.pojo.StudentAnswersRequest;
import com.java.jkpot.api.request.pojo.UpdateSectionalMockRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.api.response.pojo.StudentSectionalAnswerResponse;
import com.java.jkpot.dao.CountersDAO;
import com.java.jkpot.dao.ExamsDAO;
import com.java.jkpot.dao.StudentsSectionalMarksDAO;
import com.java.jkpot.model.ExamSyllabus;
import com.java.jkpot.model.Exams;
import com.java.jkpot.model.SectionalMocks;
import com.java.jkpot.model.StudentsSectionalMarks;
import com.java.jkpot.model.Users;
import com.java.jkpot.repositories.SectionalMockRepository;

@Service
public class SectionalMockServiceImpl implements SectionalMockService {

	@Autowired
	private SectionalMockRepository sectionalMockRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CountersDAO sequence;
	@Autowired
	private StudentsSectionalMarksDAO studentsSectionalMarksDAO;
	@Autowired
	private ExamsDAO examsDAO;

	@Override
	public ResponseEntity<RestResponse> findSectionalMockBySectionalIdAndSubSectionalId( int examId, int sectionalId, int subSectionalId, String userId) {

		StudentsSectionalMarks sectionalMarks = studentsSectionalMarksDAO.checkIfStudentHasAlreadyGivenTheMock(examId, sectionalId, subSectionalId, userId);

		if (sectionalMarks == null) {

			List<SectionalMocks> sectionalMocks =  sectionalMockRepository.findSectionalMockByExamIdAndSectionalIdAndSubSectionalId(examId, sectionalId, subSectionalId);

			if (sectionalMocks.size() > 0) {
	
				RestResponse response = new RestResponse("SUCCESS", sectionalMocks, 200);
	
				return ResponseEntity.ok(response);
			}else {
	
				RestResponse response = new RestResponse("FAILURE", "data not found", 404);
	
				return ResponseEntity.status(404).body(response);
			}
		}else {

			List<SectionalMocks> sectionalMocks =  sectionalMockRepository.findByExamIdAndSectionalIdAndSubSectionalId(examId, sectionalId, subSectionalId);

			RestResponse response = new RestResponse("TAKEN", sectionalMocks, 100);
			
			return ResponseEntity.status(200).body(response);
		}
	}

	@Override
	public ResponseEntity<RestResponse> fetchStudentsAnalysis(int examId, int sectionalId, int subSectionalId, String userId) {

		StudentsSectionalMarks sectionalMarks = studentsSectionalMarksDAO.checkIfStudentHasAlreadyGivenTheMock(examId, sectionalId, subSectionalId, userId);
		List<Document> studentList = studentsSectionalMarksDAO.fetchHighestMarksOfStudents(examId, sectionalId, subSectionalId, true);

		Map<String, Object> obj = new HashMap<String, Object>();

		double marks = sectionalMarks.getTotalMarks();
		String marksInString = String.valueOf(marks);

		if (marksInString.contains(".0"))
			obj.put("totalMarks", (int)marks);
		else
			obj.put("totalMarks", marks);

		obj.put("_id", sectionalMarks.getUserId());
		obj.put("userName", sectionalMarks.getUserName());
		obj.put("section", sectionalMarks.getSectionalName());
		obj.put("subSection", sectionalMarks.getSubSectionName());

		int rank = studentList.indexOf(new Document(obj))+1;

		StudentSectionalAnswerResponse finalResponse = new StudentSectionalAnswerResponse();
		
		finalResponse.setStudentsSectionalMarks(sectionalMarks);
		finalResponse.setRanking(rank);
		finalResponse.setTotalStudents(studentList.size());
		finalResponse.setTopStudents(this.findHighestMarksOfStudents(examId, sectionalId, subSectionalId, userId).getData());

		RestResponse response = new RestResponse("SUCCESS", finalResponse, 200);
		return ResponseEntity.status(200).body(response);
	}

	@Override
	public ResponseEntity<RestResponse> updateSectionalMockBySectionalIdAndSubSectionalId(UpdateSectionalMockRequest sectionalMockUpdateObj) {

		SectionalMocks sectionalMocks =  sectionalMockRepository.findByExamIdAndSectionalIdAndSubSectionalIdAndSectionQuestionNo(
				sectionalMockUpdateObj.getExamId(), sectionalMockUpdateObj.getSectionalId(), sectionalMockUpdateObj.getSubSectionalId(),
				sectionalMockUpdateObj.getSectionQuestionNo());

		if (sectionalMocks != null) {

			if (sectionalMockUpdateObj.getAnswer() != null)
				sectionalMocks.setAnswer(sectionalMockUpdateObj.getAnswer());

			if (sectionalMockUpdateObj.getImageName() != null) {
				sectionalMocks.setImageAdded(true);
				sectionalMocks.setImageName(sectionalMockUpdateObj.getImageName());
			}

			if (sectionalMockUpdateObj.getOptions() != null && sectionalMockUpdateObj.getOptions().size() == 4)
				sectionalMocks.setOptions(sectionalMockUpdateObj.getOptions());

			if (sectionalMockUpdateObj.getQuestion() != null)
				sectionalMocks.setQuestion(sectionalMockUpdateObj.getAnswer());

			if (sectionalMockUpdateObj.getSectionName() != null)
				sectionalMocks.setSectionName(sectionalMockUpdateObj.getSectionName());

			sectionalMockRepository.save(sectionalMocks);

			RestResponse response = new RestResponse("SUCCESS", sectionalMocks, 200);
			return ResponseEntity.ok(response);
		}else {
			RestResponse response = new RestResponse("FAILURE", "data not found", 404);

			return ResponseEntity.status(404).body(response);
		}
	}

	@Override
	public ResponseEntity<RestResponse> findStudentMarks(StudentAnswersRequest studentAnswersRequest) {

		List<SectionalMocks> sectionalMocks =  sectionalMockRepository.findByExamIdAndSectionalIdAndSubSectionalId(studentAnswersRequest.getExamId(), 
				studentAnswersRequest.getSectionalId(),studentAnswersRequest.getSubSectionId());

		if (sectionalMocks.size() > 0 && studentAnswersRequest.getUserId() != null && sectionalMocks.size() == studentAnswersRequest.getAnswers().size()) {

			double correctAnswer = 0;
			double incorrectAnswer = 0;
			int skippedQuestion = 0;
			List<Integer> correctAnswers = new LinkedList<Integer>();
			List<Integer> incorrectAnswers = new LinkedList<Integer>();
			List<Integer> skippedQuestions = new LinkedList<Integer>();

			for (int i = 0; i <sectionalMocks.size(); i++) {

				if (studentAnswersRequest.getAnswers().get(i) != null && studentAnswersRequest.getAnswers().get(i).length() > 0 && 
				sectionalMocks.get(i).getAnswer().equalsIgnoreCase(studentAnswersRequest.getAnswers().get(i))) {
					correctAnswer = correctAnswer+1;
					correctAnswers.add(sectionalMocks.get(i).getSectionQuestionNo());
				}else if (studentAnswersRequest.getAnswers().get(i) != null && studentAnswersRequest.getAnswers().get(i).length() > 0 && 
				!sectionalMocks.get(i).getAnswer().equalsIgnoreCase(studentAnswersRequest.getAnswers().get(i))) {
					incorrectAnswer = incorrectAnswer+1;
					incorrectAnswers.add(sectionalMocks.get(i).getSectionQuestionNo());
				}else {
					skippedQuestion += 1;
					skippedQuestions.add(sectionalMocks.get(i).getSectionQuestionNo());
				}
			}

			double totalMarks = correctAnswer-incorrectAnswer*0.25;

			StudentsSectionalMarks sectionalMarks = new StudentsSectionalMarks();

			sectionalMarks.setCorrectAnswers(correctAnswer);
			sectionalMarks.setIncorrectAnswers(incorrectAnswer);
			sectionalMarks.setCorrectQuestions(correctAnswers);
			sectionalMarks.setIncorrectQuestions(incorrectAnswers);
			sectionalMarks.setSkippedQuestion(skippedQuestion);
			sectionalMarks.setSkippedQuestions(skippedQuestions);
			sectionalMarks.setSectionalId(studentAnswersRequest.getSectionalId());
			sectionalMarks.setSubSectionalId(studentAnswersRequest.getSubSectionId());
			sectionalMarks.setTotalMarks(totalMarks);
			sectionalMarks.setExamId(studentAnswersRequest.getExamId());
			sectionalMarks.setStudentSectionalMarksId(sequence.getNextSequenceOfField("studentSectionalMarksId"));
			sectionalMarks.setUserId(studentAnswersRequest.getUserId());
			sectionalMarks.setMockRecordedDate(LocalDate.now());
			sectionalMarks.setStudentsAnswer(studentAnswersRequest.getAnswers());

			Users user =  mongoTemplate.findOne(Query.query(Criteria.where("userId").is(studentAnswersRequest.getUserId())), Users.class);

			if (user != null)
				sectionalMarks.setUserName(user.getFirstName()+" "+ user.getLastName());
			else
				sectionalMarks.setUserName("User_"+ String.format("%06d", new Random().nextInt()));

			ExamSyllabus sectionalObj = mongoTemplate.findOne(Query.query(Criteria.where("topicId").is(studentAnswersRequest.getSectionalId()))
					.addCriteria(Criteria.where("subTopicId").is(studentAnswersRequest.getSubSectionId()))
					.addCriteria(Criteria.where("examId").is(studentAnswersRequest.getExamId())), ExamSyllabus.class);

			if (sectionalObj != null) {
				sectionalMarks.setSectionalName(sectionalObj.getTopic());
				sectionalMarks.setSubSectionName(sectionalObj.getSubTopic());
			}

			//remove older record
//				if (user != null)
//					mongoTemplate.remove(Query.query(Criteria.where("userId").is(user.getUserId()))
//							.addCriteria(Criteria.where("subSectionalId").is(studentAnswersRequest.getSubSectionId()))
//							.addCriteria(Criteria.where("sectionalId").is(studentAnswersRequest.getSectionalId()))
//							.addCriteria(Criteria.where("totalMarks").lte(totalMarks))
//							.addCriteria(Criteria.where("examId").is(studentAnswersRequest.getExamId())), StudentsSectionalMarks.class);
			sectionalMarks.setStatus("taken");

			mongoTemplate.save(sectionalMarks, "students_sectional_marks");

			List<Document> studentList = studentsSectionalMarksDAO.fetchHighestMarksOfStudents(studentAnswersRequest.getExamId(), studentAnswersRequest.getSectionalId(),
									studentAnswersRequest.getSubSectionId(), true);

			Map<String, Object> obj = new HashMap<String, Object>();

			double marks = sectionalMarks.getTotalMarks();
			String marksInString = String.valueOf(marks);

			if (marksInString.contains(".0"))
				obj.put("totalMarks", (int)marks);
			else
				obj.put("totalMarks", marks);
			
			obj.put("_id", sectionalMarks.getUserId());
			obj.put("userName", sectionalMarks.getUserName());
			obj.put("section", sectionalMarks.getSectionalName());
			obj.put("subSection", sectionalMarks.getSubSectionName());

			int rank = studentList.indexOf(new Document(obj))+1;

			StudentSectionalAnswerResponse finalResponse = new StudentSectionalAnswerResponse();

			finalResponse.setStudentsSectionalMarks(sectionalMarks);
			finalResponse.setRanking(rank);
			finalResponse.setTotalStudents(studentList.size());
			finalResponse.setTopStudents(this.findHighestMarksOfStudents(studentAnswersRequest.getExamId(), studentAnswersRequest.getSectionalId(),
					studentAnswersRequest.getSubSectionId(), studentAnswersRequest.getUserId()).getData());

			RestResponse response = new RestResponse("SUCCESS", finalResponse, 200);
			return ResponseEntity.ok(response);
		}else {
			RestResponse response = new RestResponse("FAILURE", "Sectional Mock not exists", 404);
			return ResponseEntity.status(404).body(response);
		}
	}

	@Override
	public RestResponse findHighestMarksOfStudents(int examId, int sectionalId, int subSectionalId, String userId) {

		if (sectionalId > 0 && subSectionalId > 0 || userId != null) {

			List<Document> stu = studentsSectionalMarksDAO.fetchHighestMarksOfStudents(examId, sectionalId, subSectionalId, false);

			if (stu != null && stu.size() == 10) {
				if (stu.stream().map(e->e.get("_id")).collect(Collectors.toList()).contains(userId)) {

					RestResponse response = new RestResponse("SUCCESS", stu, 200);

					return response;
				}else {
					Document usersScore = studentsSectionalMarksDAO.fetchHighestMarksOfStudent(examId, sectionalId, subSectionalId, userId);
					stu.add(usersScore);
					
					RestResponse response = new RestResponse("SUCCESS", stu, 200);
					return response;
				}
			}else if(stu != null && stu.size() > 0) {
				RestResponse response = new RestResponse("SUCCESS", stu, 200);
				return response;
			}else {
				
				RestResponse response = new RestResponse("SUCCESS", "Nobody given the test yet", 409);
				return response;
			}
		}else {
			RestResponse response = new RestResponse("FAILURE", "Missing fields", 403);
			return response;
		}
	}
	
	@Override
	public ResponseEntity<RestResponse> fetchAttemptedMocksOfUser(String userId) {

		if (userId != null && userId.length() > 0) {

			List<Document> usersMocksList = studentsSectionalMarksDAO.findStudentsSectionalMocksByStudentId(userId);
			Set<Integer> examIds = usersMocksList.stream().map(e->e.getInteger("examId")).collect(Collectors.toSet());
			
			List<Exams> exams = examsDAO.findAllExams(examIds);

			HashMap<String, HashMap<String, List<Document>>> objFinal = new HashMap<>();

			if (usersMocksList != null && usersMocksList.size() > 0) {

				for(Exams eachExam : exams) {

					HashMap<String, List<Document>> objs = new HashMap<String, List<Document>>();

					for (String each : usersMocksList.stream().filter(e1 -> e1.getInteger("examId") == eachExam.getExamId())
							.map(e->e.getString("Section")).collect(Collectors.toList())) {

						objs.put(each, usersMocksList.stream().filter(e->e.getString("Section").equals(each)
								&& e.getInteger("examId") == eachExam.getExamId()).collect(Collectors.toList()));
					}

					objFinal.put(eachExam.getExamName(), objs);
				}
				RestResponse response = new RestResponse("SUCCESS", objFinal, 200);

				return ResponseEntity.ok(response);

//				List<Integer> sectionalId = usersMocksList.stream().map(e->e.getInteger("sectionalId")).collect(Collectors.toList());
//				List<Integer> subSectionalId = usersMocksList.stream().map(e->e.getInteger("subSectionalId")).collect(Collectors.toList());
//				Set<Integer> examId = usersMocksList.stream().map(e->e.getInteger("examId")).collect(Collectors.toSet());
//
//				LinkedHashMap<String, List<StudentsRankingResponse>> finalResponse = new LinkedHashMap<String, List<StudentsRankingResponse>>();
//			
//				for (Integer eachExamId : examId) {
//
//					for (int i = 0; i < subSectionalId.size(); i++) {
//						
//						List<StudentsRankingResponse> usersRankingInMocks = new ArrayList<>();
//
//						RestTemplate restTemplate = new RestTemplate();
//
//						HttpHeaders headers = new HttpHeaders();
////						headers.add("Authorization", "Bearer *************");
//						headers.add("content-type", "application/json"); // maintain REST-API
//	
//						// query is a grapql query wrapped into a String
//						String query = "http://localhost:8080/sectionalMock/fetch/top/students/"+eachExamId+"/"+sectionalId.get(i)+"/"+subSectionalId.get(i)+"/"+userId;
//						try {
//							ResponseEntity<String> response = restTemplate.getForEntity(query, String.class);
//
//							@SuppressWarnings("deprecation")
//							JsonObject jsonObject = new JsonParser().parse(response.getBody()).getAsJsonObject();
//		
//							JsonArray jsonMocksArray = jsonObject.get("data").getAsJsonArray();
//
//							for (JsonElement each : jsonMocksArray) {
//
//								try {
//									StudentsRankingResponse sectionalMarks = new StudentsRankingResponse();
//	
//									sectionalMarks.setUserId(each.getAsJsonObject().get("_id").getAsString());
//									sectionalMarks.setTotalMarks(each.getAsJsonObject().get("totalMarks").getAsDouble());
//									sectionalMarks.setUserName(each.getAsJsonObject().get("userName").getAsString());
//									sectionalMarks.setSectionalName(each.getAsJsonObject().get("section").getAsString());
//									sectionalMarks.setSubSectionName(each.getAsJsonObject().get("subSection").getAsString());
//	
//									usersRankingInMocks.add(sectionalMarks);
//								}catch(Exception e) {
//									continue;
//								}
//							}
//						}catch(HttpClientErrorException e) {
//	
//							RestResponse response = new RestResponse("FAILURE", e.getLocalizedMessage(), e.getRawStatusCode());
//
//							return ResponseEntity.status(e.getRawStatusCode()).body(response);
//						}
//
//						finalResponse.put(usersRankingInMocks.get(0).getSubSectionName(), usersRankingInMocks);
//					}
//				}
//
//				RestResponse response = new RestResponse("SUCCESS", finalResponse, 200);
//
//				return ResponseEntity.ok(response);
			}else {
				RestResponse response = new RestResponse("SUCCESS", "Student has not given any mock test.", 204);
				return ResponseEntity.status(200).body(response);
			}
		}else {

			RestResponse response = new RestResponse("SUCCESS", "No data available.", 204);

			return ResponseEntity.status(200).body(response);
		}
	}
}