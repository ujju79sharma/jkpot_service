package com.java.jkpot.services;

import org.springframework.http.ResponseEntity;

import com.java.jkpot.api.request.pojo.ExamsStoreDeailsRequest;
import com.java.jkpot.api.response.pojo.RestResponse;

public interface ExamsStoreService {

	ResponseEntity<RestResponse> createExamMocksInExamStore(ExamsStoreDeailsRequest examStoreDetailsRequest);

	ResponseEntity<RestResponse> findAllExamsMocksInExamStore();

	ResponseEntity<RestResponse> findExamMockInExamStore(int examStoreId);

	ResponseEntity<RestResponse> deleteExamsMocksInExamStore(int examsStoreId);

}
