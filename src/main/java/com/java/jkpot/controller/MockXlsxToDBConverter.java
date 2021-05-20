package com.java.jkpot.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.dao.CountersDAO;
import com.java.jkpot.dao.ExamsDAO;
import com.java.jkpot.model.ExamSyllabus;
import com.java.jkpot.model.FullLengthMockSections;
import com.java.jkpot.model.FullLengthMocks;
import com.java.jkpot.model.MockExams;
import com.java.jkpot.model.SectionalMocks;
import com.poiji.bind.Poiji;

@RestController
@RequestMapping("/import")
public class MockXlsxToDBConverter {

	@Autowired
	private CountersDAO sequence;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private ExamsDAO examsDAO;

	@PostMapping(path = "/data/xlsx/mcq/{examId}/{sectionalId}/{subSectionalId}")
	public ResponseEntity<RestResponse> uploadExcelToSectionalMockDB(@PathVariable(value = "sectionalId") int sectionalId,
			@PathVariable(value = "subSectionalId") int subSectionalId, @PathVariable(value = "examId") int examId,
			@RequestPart(value = "file") MultipartFile file) throws IOException {

		if (mongoTemplate.findOne(Query.query(Criteria.where("sectionalId").is(sectionalId))
				.addCriteria(Criteria.where("subSectionalId").is(subSectionalId))
				.addCriteria(Criteria.where("examId").is(examId)), SectionalMocks.class) != null) {

			RestResponse response = new RestResponse("FAILURE", "Data already inserted", 400);
			return ResponseEntity.status(409).body(response);
		}

		else {
			String sectionalName = mongoTemplate
					.findOne(Query.query(Criteria.where("topicId").is(sectionalId)), ExamSyllabus.class).getTopic();

			File files = new File("jkpot/" + file.getOriginalFilename());
			files.createNewFile();
			FileOutputStream fout = new FileOutputStream(files);
			fout.write(file.getBytes());
			fout.close();

			List<MockExams> mockQuestions = Poiji.fromExcel(files, MockExams.class);
			ZipSecureFile.setMinInflateRatio(-1.0d);

			for (MockExams each : mockQuestions) {

				SectionalMocks mock = new SectionalMocks();

				List<String> options = new ArrayList<>();

				String question = each.getQuestion().trim();
				mock.setQuestion(question);

				options.add(each.getOptionA());
				options.add(each.getOptionB());
				options.add(each.getOptionC());
				options.add(each.getOptionD());

				mock.setOptions(options);
				mock.setAnswer(each.getAnswer().trim());
				mock.setSectionName(sectionalName);
				mock.setExamId(examId);
				mock.setSectionQuestionNo(each.getSectionQuestionNo());
				mock.setSectionalMockId(sequence.getNextSequenceOfField("sectionalMockId"));
				if (each.getImageOption().equals("NO"))
					mock.setImageAdded(false);
				else
					mock.setImageAdded(true);
				mock.setSectionalId(sectionalId);
				mock.setSubSectionalId(subSectionalId);

				mongoTemplate.save(mock, "sectional_mocks");
			}

			RestResponse response = new RestResponse("SUCCESS", mockQuestions, 200);
			files.delete(); // delete the file
			return ResponseEntity.ok(response);
		}
	}
	
	@PostMapping(path = "/data/xlsx/mcq/{examId}/{mockname}")
	public ResponseEntity<RestResponse> uploadExcelInFullLengthMockDB(
			@PathVariable(value = "examId") int examId,
			@PathVariable(value = "mockname") String mockName,
			@RequestPart(value = "file") MultipartFile file) throws IOException {

		if (mongoTemplate.findOne(Query.query(Criteria.where("fullLengthMockName").is(mockName))
				.addCriteria(Criteria.where("examId").is(examId)), FullLengthMocks.class) != null) {

			RestResponse response = new RestResponse("FAILURE", "Data already inserted", 409);
			return ResponseEntity.status(409).body(response);
		}

		else {
			String examName = examsDAO.getExamName(examId);

			File files = new File("jkpot/" + file.getOriginalFilename());
			files.createNewFile();
			FileOutputStream fout = new FileOutputStream(files);
			fout.write(file.getBytes());
			fout.close();

			List<FullLengthMocks> mockQuestions = Poiji.fromExcel(files, FullLengthMocks.class);
			ZipSecureFile.setMinInflateRatio(-1.0d);
			
			TreeMap<String, Integer> sectionNamesAndQuestions = new TreeMap<>();
			
			int mockId = sequence.getNextSequenceOfField("mockId");

			for (FullLengthMocks each : mockQuestions) {

				FullLengthMocks mock = new FullLengthMocks();

				List<String> options = new ArrayList<>();
				
				mock.setExamId(examId);
				mock.setExamName(examName);
				mock.setQuestionNo(each.getQuestionNo());
				mock.setMockId(mockId);
				mock.setSectionName(each.getSectionName());

				mock.setSectionQuestionNo(each.getSectionQuestionNo());
				mock.setSectionNo(each.getSectionNo());
				mock.setFullLengthMockName(mockName);
				String question = each.getQuestion().trim();
				mock.setQuestion(question);

				options.add(each.getOptionA());
				options.add(each.getOptionB());
				options.add(each.getOptionC());
				options.add(each.getOptionD());

				mock.setOptions(options);
				mock.setAnswer(each.getAnswer().trim());
				mock.setFullLengthMockId(sequence.getNextSequenceOfField("fullLengthMockId"));
				sectionNamesAndQuestions.put(each.getSectionName(), each.getSectionQuestionNo());
				mongoTemplate.save(mock, "full_length_mocks");
			}

			RestResponse response = new RestResponse("SUCCESS", mockQuestions, 200);
			files.delete(); // delete the file
			
			FullLengthMockSections fullLengthSections = new FullLengthMockSections();
			
			fullLengthSections.setExamId(examId);
			fullLengthSections.setExamName(examName);
			fullLengthSections.setMockName(mockName);
			fullLengthSections.setSectionInformation(sectionNamesAndQuestions);
			fullLengthSections.setId(sequence.getNextSequenceOfField("fullLengthMockSectionId"));

			mongoTemplate.save(fullLengthSections, "full_length_mock_sections");
			
			return ResponseEntity.ok(response);
		}
	}
}