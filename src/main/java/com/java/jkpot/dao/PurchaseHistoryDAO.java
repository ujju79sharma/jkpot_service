package com.java.jkpot.dao;

import com.java.jkpot.model.PurchaseHistory;

public interface PurchaseHistoryDAO {

	PurchaseHistory checkAlreadyPurchasedByUser(String userId, int examId);

	
}
