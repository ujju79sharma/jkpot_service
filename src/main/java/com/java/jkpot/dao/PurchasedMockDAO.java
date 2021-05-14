package com.java.jkpot.dao;

import com.java.jkpot.model.PurchasedMocks;

public interface PurchasedMockDAO {

	PurchasedMocks checkAlreadyPurchasedByUser(String userId, int examId);

}
