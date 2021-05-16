package com.java.jkpot.dao;

import java.util.List;

import com.java.jkpot.model.PurchaseHistory;

public interface PurchaseHistoryDAO {

	PurchaseHistory checkAlreadyPurchasedByUser(String userId, int examId);

	List<PurchaseHistory> fetchPurchaseHistoryOfUser(String userId);

	
}
