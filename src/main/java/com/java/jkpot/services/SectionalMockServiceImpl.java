package com.java.jkpot.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.java.jkpot.api.request.pojo.StudentAnswersRequest;
import com.java.jkpot.api.request.pojo.UpdateSectionalMockRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.api.response.pojo.StudentsRankingResponse;
import com.java.jkpot.dao.CountersDAO;
import com.java.jkpot.dao.StudentsSectionalMarksDAO;
import com.java.jkpot.model.ExamSyllabus;
import com.java.jkpot.model.SectionalMocks;
import com.java.jkpot.model.StudentsSectionalMarks;
import com.java.jkpot.model.Users;
import com.java.jkpot.repositories.SectionalMockRepository;
import com.mongodb.util.JSONParseException;

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
	
	@Override
	public ResponseEntity<RestResponse> findSectionalMockBySectionalIdAndSubSectionalId( int examId, int sectionalId, int subSectionalId) {

		List<SectionalMocks> sectionalMocks =  sectionalMockRepository.findByExamIdAndSectionalIdAndSubSectionalId(examId, sectionalId, subSectionalId);

		if (sectionalMocks.size() > 0) {

			RestResponse response = new RestResponse("SUCCESS", sectionalMocks.stream().peek(e->e.setAnswer(null)).collect(Collectors.toList()), 200);

			return ResponseEntity.ok(response);
		}else {

			RestResponse response = new RestResponse("FAILURE", "data not found", 404);

			return ResponseEntity.status(404).body(response);
		}
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

			ExamSyllabus sectionalObj = mongoTemplate.findOne(Query.query(Criteria.where("topicId").is(studentAnswersRequest.getSectionalId()))
					.addCriteria(Criteria.where("subTopicId").is(studentAnswersRequest.getSubSectionId()))
					.addCriteria(Criteria.where("examId").is(studentAnswersRequest.getExamId())), ExamSyllabus.class);

			if (sectionalObj != null) {
				sectionalMarks.setSectionalName(sectionalObj.getTopic());
				sectionalMarks.setSubSectionName(sectionalObj.getSubTopic());
			}

			Users user =  mongoTemplate.findOne(Query.query(Criteria.where("userId").is(studentAnswersRequest.getUserId())), Users.class);

			if (user != null)
				sectionalMarks.setUserName(user.getFirstName()+" "+ user.getLastName());
			else
				sectionalMarks.setUserName("User_"+ String.format("%06d", new Random().nextInt()));

			//remove older record
			if (user != null)
				mongoTemplate.remove(Query.query(Criteria.where("userId").is(user.getUserId()))
						.addCriteria(Criteria.where("subSectionalId").is(studentAnswersRequest.getSubSectionId()))
						.addCriteria(Criteria.where("sectionalId").is(studentAnswersRequest.getSectionalId()))
						.addCriteria(Criteria.where("totalMarks").lte(totalMarks))
						.addCriteria(Criteria.where("examId").is(studentAnswersRequest.getExamId())), StudentsSectionalMarks.class);

			mongoTemplate.save(sectionalMarks, "students_sectional_marks");

			RestResponse response = new RestResponse("SUCCESS", sectionalMarks, 200);
			return ResponseEntity.ok(response);
		}else {
			RestResponse response = new RestResponse("FAILURE", "Sectional Mock not exists", 404);
			return ResponseEntity.status(404).body(response);
		}
	}

	@Override
	public ResponseEntity<RestResponse> findHighestMarksOfStudents(int examId, int sectionalId, int subSectionalId, String userId) {

		if (sectionalId > 0 && subSectionalId > 0 || userId != null) {

			List<StudentsSectionalMarks> students = studentsSectionalMarksDAO.findByExamIdAndSectionalIdAndSubSectionalId(examId, sectionalId, subSectionalId);

			if (students.size() > 0) {

				students.sort(new StudentsSorting());
	
				HashMap<String, StudentsSectionalMarks> studentMarks = new HashMap<String, StudentsSectionalMarks>();
	
				for (StudentsSectionalMarks each : students)
					studentMarks.put(each.getUserId(), each);
	
				studentMarks = sortByValue(studentMarks);
				
				if (studentMarks.size() >= 10) {
					
					if (studentMarks.keySet().contains(userId)) { // if userId exist in top 10 list
	
						RestResponse response = new RestResponse("SUCCESS", studentMarks.values(), 200);
						
						return ResponseEntity.ok(response);
					}else {
					
						students = students.stream().filter(e->e.getUserId().equals(userId)).collect(Collectors.toList());
						
						StudentsSectionalMarks usersLatestMarks = students.get(studentMarks.size()-1);
						
						studentMarks.put(userId, usersLatestMarks);
						
						studentMarks = sortByValue(studentMarks);
						
						RestResponse response = new RestResponse("SUCCESS", studentMarks.values(), 200);
						
						return ResponseEntity.ok(response);
					}
				}else {
					RestResponse response = new RestResponse("SUCCESS", studentMarks.values(), 200);
					
					return ResponseEntity.ok(response);
				}
			}else {
				RestResponse response = new RestResponse("SUCCESS", "No mock given by the user.", 204);
				
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		}else {
			
			RestResponse response = new RestResponse("FAILURE", "please insert userId or sectionalId or subSectionalId", 400);
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	@Override
	public ResponseEntity<RestResponse> findHighestMarksOfStudentsInAllMocks(String userId) {
		
		if (userId != null && userId.length() > 0) {
		
			List<StudentsSectionalMarks> usersMocksList = studentsSectionalMarksDAO.findStudentsSectionalMocksByStudentId(userId);

			HashMap<Integer, Integer> usersMocks = new HashMap<Integer, Integer>();

			for (StudentsSectionalMarks each : usersMocksList) {
				usersMocks.put(each.getSubSectionalId(), each.getSectionalId());
			}

			if (usersMocks.size() > 0) {

				List<Integer> sectionalId = new ArrayList<>(usersMocks.values());
				List<Integer> subSectionalId = new ArrayList<>(usersMocks.keySet());
				Set<Integer> examId = usersMocksList.stream().map(e->e.getExamId()).collect(Collectors.toSet());
				
				LinkedHashMap<String, List<StudentsRankingResponse>> finalResponse = new LinkedHashMap<String, List<StudentsRankingResponse>>();
			
				for (Integer eachExamId : examId) {

					for (int i = 0; i < subSectionalId.size(); i++) {
						
						List<StudentsRankingResponse> usersRankingInMocks = new ArrayList<>();
						
						RestTemplate restTemplate = new RestTemplate();
	
						HttpHeaders headers = new HttpHeaders();
						headers.add("Authorization", "Bearer *************");
						headers.add("content-type", "application/json"); // maintain graphql
	
						// query is a grapql query wrapped into a String
						String query = "http://localhost:8080/sectionalMock/fetch/top/students/"+eachExamId+"/"+sectionalId.get(i)+"/"+subSectionalId.get(i)+"/"+userId;

						try {
							ResponseEntity<String> response = restTemplate.getForEntity(query, String.class);

							@SuppressWarnings("deprecation")
							JsonObject jsonObject = new JsonParser().parse(response.getBody()).getAsJsonObject();
		
							JsonArray jsonMocksArray = jsonObject.get("data").getAsJsonArray();

							for (JsonElement each : jsonMocksArray) {

								try {
									StudentsRankingResponse sectionalMarks = new StudentsRankingResponse();
	
									sectionalMarks.setUserId(each.getAsJsonObject().get("userId").getAsString());
									sectionalMarks.setTotalMarks(each.getAsJsonObject().get("totalMarks").getAsDouble());
									sectionalMarks.setUserName(each.getAsJsonObject().get("userName").getAsString());
									sectionalMarks.setSectionalName(each.getAsJsonObject().get("sectionalName").getAsString());
									sectionalMarks.setSubSectionName(each.getAsJsonObject().get("subSectionName").getAsString());
	
									usersRankingInMocks.add(sectionalMarks);
								}catch(JSONParseException e) {
									continue;
								}
							}
						}catch(HttpClientErrorException e) {
	
							RestResponse response = new RestResponse("FAILURE", e.getLocalizedMessage(), e.getRawStatusCode());

							return ResponseEntity.status(e.getRawStatusCode()).body(response);
						}

						finalResponse.put(usersRankingInMocks.get(0).getSubSectionName(), usersRankingInMocks);
					}
				}

				RestResponse response = new RestResponse("SUCCESS", finalResponse, 200);

				return ResponseEntity.ok(response);
			}else {
				RestResponse response = new RestResponse("SUCCESS", "Student has not given any mock test.", 204);
				return ResponseEntity.status(200).body(response);
			}
		}else {

			RestResponse response = new RestResponse("SUCCESS", "No data available.", 204);

			return ResponseEntity.status(200).body(response);
		}
	}

	public static HashMap<String, StudentsSectionalMarks> sortByValue(HashMap<String, StudentsSectionalMarks> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, StudentsSectionalMarks>> list =
               new LinkedList<Map.Entry<String, StudentsSectionalMarks> >(hm.entrySet());
  
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, StudentsSectionalMarks> >() {
            public int compare(Map.Entry<String, StudentsSectionalMarks> o1, 
                               Map.Entry<String, StudentsSectionalMarks> o2)
            {
                return -1*Double.compare(o1.getValue().getTotalMarks(),o2.getValue().getTotalMarks());
            }
        });
          
        // put data from sorted list to hashmap 
        HashMap<String, StudentsSectionalMarks> temp = new LinkedHashMap<String, StudentsSectionalMarks>();
        for (Map.Entry<String, StudentsSectionalMarks> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
	}
}

class StudentsSorting implements Comparator<StudentsSectionalMarks> {

	@Override
	public int compare(StudentsSectionalMarks o1, StudentsSectionalMarks o2) {
		
		return Double.compare(o1.getTotalMarks(), o2.getTotalMarks());
	}
}
