package com.java.jkpot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.jkpot.api.request.pojo.ExamControllerDetailsRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.services.ExamConductorsService;

@RestController
@RequestMapping("/examconductor")
public class ExamConductorsController {

	@Autowired
	private ExamConductorsService examConductorService;
	
	@PostMapping(value = "/create")
	public ResponseEntity<RestResponse> createExamConductor(@RequestBody ExamControllerDetailsRequest examConductorDetailsRequest) {

		return examConductorService.createExamConductor(examConductorDetailsRequest.getExamControllerId(), examConductorDetailsRequest.getName(), 
					examConductorDetailsRequest.getLogo());
	}
	
	@GetMapping(value = "/read/{examcontrollerid")
	public ResponseEntity<RestResponse> readExamConductor(@PathVariable(value = "examcontrollerid") int examControllerId) {
		
		return examConductorService.fetchExamConductor(examControllerId);
	}
	
	@GetMapping(value = "/readall")
	public ResponseEntity<RestResponse> readAllExamConductors() {

		return examConductorService.fetchExamConductors();
	}
	
	@DeleteMapping(value ="/delete/{examcontrollerid")
	public ResponseEntity<RestResponse> deleteExamConductor(@PathVariable(value = "examcontrollerid") int examControllerId) {

		return examConductorService.deleteExamConductor(examControllerId);
	}
}
