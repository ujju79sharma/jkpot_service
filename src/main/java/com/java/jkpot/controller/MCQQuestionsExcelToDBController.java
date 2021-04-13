package com.java.jkpot.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.dao.CountersDAO;
import com.java.jkpot.model.MockExams;
import com.java.jkpot.repositories.MockExamRepository;

@RestController
@RequestMapping("/import")
public class MCQQuestionsExcelToDBController {

	@Autowired
	private MockExamRepository mockExamRepository;
	@Autowired
	private CountersDAO countersDAO;

	@PostMapping(path = "/excel/data/mcq/{examId}")
	public ResponseEntity<Object> bulkImportExamSyllabus(@RequestPart(value = "file") MultipartFile file, @PathVariable(value = "examId") int examId)
			throws IOException {

		File files = new File("jkpot/" + file.getOriginalFilename());
		files.createNewFile();
		FileOutputStream fout = new FileOutputStream(files);
		fout.write(file.getBytes());
		fout.close();

		List<MockExams> mockExamList = getMockExamDetails("jkpot/" + file.getOriginalFilename());

		for (MockExams each : mockExamList) {
			each.setExamId(examId);
			each.setMockExamId((int)countersDAO.getNextSequenceOfField("mockExamId"));
		}

		
		mockExamRepository.saveAll(mockExamList);
		
		files.delete(); // delete the CSV file

		RestResponse response = new RestResponse("SUCCESS", mockExamList, 200);

		return ResponseEntity.status(200).body(response);

	}

	private static List<MockExams> getMockExamDetails(String file) {

		List<MockExams> mockExamList = new ArrayList<>();

		Path pathToFile = Paths.get(file);
		try (BufferedReader br = Files.newBufferedReader(pathToFile, Charset.forName("ISO-8859-1"))) {
			String row = br.readLine();
			while (row != null) {
				String[] attributes = row.split(",");
				MockExams mockExam = getOneMockExamObject(attributes);
				if (mockExam != null) {
					mockExamList.add(mockExam);
				}
				row = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mockExamList;
	}

	private static MockExams getOneMockExamObject(String[] attributes) {

		String sectionName;
		int sectionQuestionNo;
		String question;
		String answer;
		List<String> options = new ArrayList<>();
		boolean imageAdded;
		String imageName;

		try {
			sectionName = (attributes[0].trim());
		} catch (Exception e) {
			sectionName = null;
		}

		try {
			sectionQuestionNo = Integer.parseInt(attributes[1].trim());
		} catch (Exception e) {
			sectionQuestionNo = 0;
		}

		try {
			question = (attributes[2].trim().replaceAll(" comma ", ", "));
			System.out.println(question);
			question = question.replaceAll("\n", "\n");
			System.out.println(question);
		} catch (Exception e) {
			question = null;
		}
		
		try {
			options.add(attributes[3].trim().replaceAll(" comma ", ", "));
		} catch (Exception e) {
			options = new ArrayList<>();
		}
		
		try {
			options.add(attributes[4].trim().replaceAll(" comma ", ", "));
		} catch (Exception e) {
			options = new ArrayList<>();
		}
		
		try {
			options.add(attributes[5].trim().replaceAll(" comma ", ", "));
		} catch (Exception e) {
			options = new ArrayList<>();
		}
		
		try {
			options.add(attributes[6].trim().replaceAll(" comma ", ", "));
		} catch (Exception e) {
			options = new ArrayList<>();
		}
		
		try {
			answer = attributes[7].trim();
		}catch(Exception e) {
			answer = null;
		}
		
		try {
			imageAdded = attributes[8].trim().equalsIgnoreCase("No")?false:true;
		} catch (Exception e) {
			imageAdded = false;
		}

		try {
			imageName = attributes[9];

		} catch (Exception e) {
			imageName = null;
		}

		MockExams mockExam = new MockExams();

		if (sectionQuestionNo > 0)
			mockExam.setSectionQuestionNo(sectionQuestionNo);
		if (sectionName != null && sectionName.length() > 2)
			mockExam.setSectionName(sectionName);
		if (question != null && question.length() > 2)
			mockExam.setQuestion(question);
		if (options != null && options.size() == 4)
			mockExam.setOptions(options);
		if (imageName != null && imageName.length() > 2)
			mockExam.setImageName(imageName);
		if (answer != null && answer.length() > 0)
			mockExam.setAnswer(answer);
		
		mockExam.setImageAdded(imageAdded);
		
		if (sectionQuestionNo > 0 && sectionName != null && sectionName.length() > 2 
				&& question != null && question.length() > 0 && options != null && options.size() == 4
				&& answer != null && answer.length() > 0)
			return mockExam;
		else
			return null;
	}
}