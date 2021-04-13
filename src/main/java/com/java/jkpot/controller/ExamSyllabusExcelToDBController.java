package com.java.jkpot.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.java.jkpot.model.ExamSyllabus;
import com.java.jkpot.repositories.ExamSyllabusRepository;

@RestController
@RequestMapping("/import")
public class ExamSyllabusExcelToDBController {

	@Autowired
	private ExamSyllabusRepository examSyllabusRepository;
	@Autowired
	private CountersDAO countersDAO;

	@PostMapping(path = "/excel/data/examsyllabus/{examId}")
	public ResponseEntity<Object> bulkImportExamSyllabus(@RequestPart(value = "file") MultipartFile file, @PathVariable(value = "examId") int examId)
			throws IOException {

		File files = new File("jkpot/" + file.getOriginalFilename());
		files.createNewFile();
		FileOutputStream fout = new FileOutputStream(files);
		fout.write(file.getBytes());
		fout.close();

		List<ExamSyllabus> examSyllabusList = getExamSyllabusDetails("jkpot/" + file.getOriginalFilename());

		for (ExamSyllabus each : examSyllabusList) {
			each.setExamId(examId);
			each.setExamSyllabusId((int)countersDAO.getNextSequenceOfField("examSyllabusId"));
		}
		
		examSyllabusRepository.saveAll(examSyllabusList);
		
		files.delete(); // delete the CSV file

		RestResponse response = new RestResponse("SUCCESS", examSyllabusList, 200);

		return ResponseEntity.status(200).body(response);

	}

	private static List<ExamSyllabus> getExamSyllabusDetails(String file) {

		List<ExamSyllabus> examSyllabusList = new ArrayList<>();

		Path pathToFile = Paths.get(file);
		try (BufferedReader br = Files.newBufferedReader(pathToFile)) {
			String row = br.readLine();
			while (row != null) {
				String[] attributes = row.split(",");
				ExamSyllabus examSyllabus = getOneExamSyllabus(attributes);
				if (examSyllabus != null) {
					examSyllabusList.add(examSyllabus);
				}
				row = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return examSyllabusList;
	}

	private static ExamSyllabus getOneExamSyllabus(String[] attributes) {

		int topicId;
		String topic;
		int subTopicId;
		String subTopic;
		int numberOfQuestions;

		try {
			topicId = Integer.parseInt(attributes[0].trim());
		} catch (Exception e) {
			topicId = 0;
		}
		
		try {
			topic = attributes[1].trim().replaceAll(" comma ", ", ");
		} catch (Exception e) {
			topic = null;
		}
		
		try {
			subTopicId = Integer.parseInt(attributes[2].trim());
		} catch (Exception e) {
			subTopicId = 0;
		}
		
		try {
			subTopic = attributes[3].trim().replaceAll(" comma ", ", ");
		} catch (Exception e) {
			subTopic = null;
		}

		try {
			numberOfQuestions = Integer.parseInt(attributes[4]);

		} catch (Exception e) {
			numberOfQuestions = 0;
		}

		ExamSyllabus examSyllabus = new ExamSyllabus();

		if (topicId > 0)
			examSyllabus.setTopicId(topicId);
		if (topic != null && topic.length() > 2)
			examSyllabus.setTopic(topic);
		if (subTopicId > 0)
			examSyllabus.setSubTopicId(subTopicId);
		if (subTopic != null && subTopic.length() > 2)
			examSyllabus.setSubTopic(subTopic);
		if (numberOfQuestions > 0)
			examSyllabus.setNumberOfQuestions(numberOfQuestions);

		if (topicId > 0 && subTopicId > 0 && numberOfQuestions > 0)
			return examSyllabus;
		else if (numberOfQuestions == 0 && subTopic.equalsIgnoreCase("Mock")) {
			return examSyllabus;
		}
		else
			return null;
	}
}
