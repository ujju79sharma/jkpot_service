package com.java.jkpot.dao;

import java.util.List;

import com.java.jkpot.model.PurchasedMocks;

public interface PurchasedMockDAO {

	PurchasedMocks checkAlreadyPurchasedByUser(String userId, int examId);

	List<PurchasedMocks> fetchPurchasedMocksOfUser(String userId);

}
