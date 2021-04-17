package com.java.jkpot.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.jkpot.api.request.pojo.StudentAnswersRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.dao.CountersDAO;
import com.java.jkpot.model.ExamSyllabus;
import com.java.jkpot.model.SectionalMocks;
import com.java.jkpot.model.StudentsSectionalMarks;
import com.java.jkpot.model.Users;
import com.java.jkpot.repositories.SectionalMockRepository;
import com.java.jkpot.repositories.StudentsSectionalMarksRepository;

@Service
public class SectionalMockServiceImpl implements SectionalMockService {

	@Autowired
	private SectionalMockRepository sectionalMockRepository;
	@Autowired
	private StudentsSectionalMarksRepository studentsSectionalMarksRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CountersDAO sequence;
	
	@Override
	public ResponseEntity<RestResponse> findSectionalMockBySectionalIdAndSubSectionalId(int sectionalId,
			int subSectionalId) {

		List<SectionalMocks> sectionalMocks =  sectionalMockRepository.findBySectionalIdAndSubSectionalId(sectionalId, subSectionalId);

		if (sectionalMocks.size() > 0) {

			RestResponse response = new RestResponse("SUCCESS", sectionalMocks, 200);

			return ResponseEntity.ok(response);
		}else {

			RestResponse response = new RestResponse("FAILURE", "data not found", 404);

			return ResponseEntity.status(404).body(response);
		}
	}

	@Override
	public ResponseEntity<RestResponse> findStudentMarks(StudentAnswersRequest studentAnswersRequest) {

		List<SectionalMocks> sectionalMocks =  sectionalMockRepository.findBySectionalIdAndSubSectionalId(studentAnswersRequest.getSectionalId(),
				studentAnswersRequest.getSubSectionId());

		if (sectionalMocks.size() > 0 && studentAnswersRequest.getUserId() != null) {

			double correctAnswer = 0;
			double incorrectAnswer = 0;
			int skippedQuestion = 0;
			List<Integer> correctAnswers = new LinkedList<Integer>();
			List<Integer> incorrectAnswers = new LinkedList<Integer>();
			List<Integer> skippedQuestions = new LinkedList<Integer>();
			
			for (int i = 0; i <sectionalMocks.size(); i++) {
				
				if (studentAnswersRequest.getAnswers().get(i) != null && studentAnswersRequest.getAnswers().get(i).length() > 0 && 
				sectionalMocks.get(i).getAnswer().equals(studentAnswersRequest.getAnswers().get(i))) {
					correctAnswer = correctAnswer+1;
					correctAnswers.add(sectionalMocks.get(i).getSectionQuestionNo());
				}else if (studentAnswersRequest.getAnswers().get(i) != null && studentAnswersRequest.getAnswers().get(i).length() > 0 && 
				!sectionalMocks.get(i).getAnswer().equals(studentAnswersRequest.getAnswers().get(i))) {
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
			sectionalMarks.setStudentSectionalMarksId(sequence.getNextSequenceOfField("studentSectionalMarksId"));
			sectionalMarks.setUserId(studentAnswersRequest.getUserId());
			
			ExamSyllabus sectionalObj = mongoTemplate.findOne(Query.query(Criteria.where("topicId").is(studentAnswersRequest.getSectionalId()))
					.addCriteria(Criteria.where("subTopicId").is(studentAnswersRequest.getSubSectionId())), ExamSyllabus.class);
			
			if (sectionalObj != null) {
				sectionalMarks.setSectionalName(sectionalObj.getTopic());
				sectionalMarks.setSubSectionName(sectionalObj.getSubTopic());
			}
			
			Users user =  mongoTemplate.findOne(Query.query(Criteria.where("userId").is(studentAnswersRequest.getUserId())), Users.class);
			
			if (user != null)
				sectionalMarks.setUserName(user.getFirstName()+" "+ user.getLastName());

			mongoTemplate.save(sectionalMarks, "students_sectional_marks");
			
			RestResponse response = new RestResponse("SUCCESS", sectionalMarks, 200);
			return ResponseEntity.ok(response);
		}else {
			RestResponse response = new RestResponse("FAILURE", "Sectional Mock Not Exist", 404);
			return ResponseEntity.status(404).body(response);
		}
	}

	@Override
	public ResponseEntity<RestResponse> findHighestMarksOfStudents(int sectionalId, int subSectionalId, String userId) {
		
		if (sectionalId > 0 && subSectionalId > 0 || userId != null) {

			List<StudentsSectionalMarks> students = studentsSectionalMarksRepository.findBySectionalIdAndSubSectionalId(sectionalId, subSectionalId);

			List<String> studentIds = students.stream().map(e->e.getUserId()).distinct().collect(Collectors.toList());

			students = mongoTemplate.find(Query.query(Criteria.where("userId").in(studentIds))
					.with(Sort.by(Sort.Direction.ASC, "totalMarks")), StudentsSectionalMarks.class);
			
//			students.sort(new StudentsSort());
			
			HashMap<String, StudentsSectionalMarks> studentMarks = new HashMap<String, StudentsSectionalMarks>();

			for (StudentsSectionalMarks each : students)
				studentMarks.put(each.getUserId(), each);

			studentMarks = sortByValue(studentMarks);
			
			if (studentMarks.size() >= 10) {
				
				if (studentMarks.keySet().contains(userId)) {

					RestResponse response = new RestResponse("SUCCESS", studentMarks.values(), 200);
					
					return ResponseEntity.ok(response);
				}else {
				
					StudentsSectionalMarks usersLatestMarks = mongoTemplate.findOne(Query.query(Criteria.where(userId).is(userId))
							.with(Sort.by(Sort.Direction.DESC, "studentSectionalMarksId")), StudentsSectionalMarks.class);
					
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
			
			RestResponse response = new RestResponse("FAILURE", "please insert userId or sectionalId or subSectionalId", 400);
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	class StudentsSort implements Comparator<StudentsSectionalMarks> {

		@Override
		public int compare(StudentsSectionalMarks o1, StudentsSectionalMarks o2) {
			return -1*Double.compare(o1.getTotalMarks(), o2.getTotalMarks());
		}
	}
	
	public static HashMap<String, StudentsSectionalMarks> sortByValue(HashMap<String, StudentsSectionalMarks> hm)
    {
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
