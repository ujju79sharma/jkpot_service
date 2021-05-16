package com.java.jkpot.services;

import java.time.LocalDate;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import com.java.jkpot.api.request.pojo.PurchasedMockInfoRequest;
import com.java.jkpot.api.response.pojo.RestResponse;
import com.java.jkpot.communications.MailUtils;
import com.java.jkpot.dao.CountersDAO;
import com.java.jkpot.dao.ExamsDAO;
import com.java.jkpot.dao.PurchaseHistoryDAO;
import com.java.jkpot.dao.PurchasedMockDAO;
import com.java.jkpot.model.PurchaseHistory;
import com.java.jkpot.model.PurchasedMocks;
import com.java.jkpot.model.Users;

@Service
public class PurchasedMockServiceImpl implements PurchasedMockService {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CountersDAO sequence;
	@Autowired
	private PurchasedMockDAO purchasedMockDAO;
	@Autowired
	private PurchaseHistoryDAO paymentHistoryDAO;
	@Autowired
	private ExamsDAO examsDAO;
	@Autowired
	private MailUtils emailUtil;

	@Override
	public ResponseEntity<RestResponse> addPurchasedMockOfUser(PurchasedMockInfoRequest purchasedMockInfoRequest) {
		
		if (purchasedMockInfoRequest.getUserId() != null && purchasedMockInfoRequest.getSubscriptionId() != null
				&& purchasedMockInfoRequest.getExamId() > 0 && purchasedMockInfoRequest.getPaymentId() != null &&
				purchasedMockInfoRequest.getPaymentId().length() >= 4) {

			if(purchasedMockDAO.checkAlreadyPurchasedByUser(purchasedMockInfoRequest.getUserId(), purchasedMockInfoRequest.getExamId()) == null
					&& paymentHistoryDAO.checkAlreadyPurchasedByUser(purchasedMockInfoRequest.getUserId(), purchasedMockInfoRequest.getExamId()) == null) {

				String examName = examsDAO.getExamName(purchasedMockInfoRequest.getExamId());
				
				if (examName != null) {

					PurchasedMocks purchasedMock = new PurchasedMocks();
	
					purchasedMock.setUserId(purchasedMockInfoRequest.getUserId());
					purchasedMock.setExamId(purchasedMockInfoRequest.getExamId());
					purchasedMock.setSubscriptionId(purchasedMockInfoRequest.getSubscriptionId());
					purchasedMock.setExamId(purchasedMockInfoRequest.getExamId());
					purchasedMock.setExamName(examName);
					purchasedMock.setDescription(purchasedMockInfoRequest.getDescription());
					purchasedMock.setPurchasedDate(LocalDate.now());
					purchasedMock.setPurchasedMockId(sequence.getNextSequenceOfField("purchasedMockId"));
					purchasedMock.setStatus("Purchased");
					
					Users user = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(purchasedMockInfoRequest.getUserId())), Users.class);
		
					user.setSubscriptionIds(purchasedMockInfoRequest.getSubscriptionId());
					
					PurchaseHistory purchaseHistory = new PurchaseHistory();
					
					purchaseHistory.setPurchaseHistoryId(sequence.getNextSequenceOfField("purchaseHistoryId"));
					purchaseHistory.setPaymentId(purchasedMockInfoRequest.getPaymentId());
					purchaseHistory.setSubscriptionId(purchasedMockInfoRequest.getSubscriptionId());
					purchaseHistory.setUserId(purchasedMockInfoRequest.getUserId());
					purchaseHistory.setExamId(purchasedMockInfoRequest.getExamId());
					purchaseHistory.setStatus("Purchased");
	
					mongoTemplate.save(user, "users");
					mongoTemplate.save(purchasedMock, "purchased_mocks");
					mongoTemplate.save(purchaseHistory, "purchase_history");
					
					String message = "Payment received for the user";
					String messageBody = "Payment has been received from the user name "+user.getFirstName()+" "+user.getLastName()+
							" for the exam "+ examName +"bearing userid "+ user.getUserId()+". The payment id generated for the user is "+purchasedMockInfoRequest.getPaymentId();
					String emailId = "rockysukhi0907@gmail.com";
	
					RestResponse response = new RestResponse("SUCCESS", purchasedMock, 200);
					
					try {
						emailUtil.sendMail(emailId, message, messageBody);
					}catch(MailException e) {
						RestResponse errorResponse = new RestResponse("FAILURE", e.getLocalizedMessage(), 500);
						
						return ResponseEntity.status(500).body(errorResponse);
					}
					return ResponseEntity.ok(response);
				}else {
					RestResponse response = new RestResponse("FAILURE", "No exam exists", 401);

					return ResponseEntity.status(401).body(response);
				}
			}else {
				RestResponse response = new RestResponse("SUCCESS", "Already purchased the exam", 200);

				return ResponseEntity.ok(response);
			}
		}else {
			
			RestResponse response = new RestResponse("FAILURE", "missing request body info", 401);

			return ResponseEntity.status(401).body(response);
		}
	}

	@Override
	public ResponseEntity<RestResponse> deletePurchasedMockOfUser(PurchasedMockInfoRequest purchasedMockInfoRequest) {

		if (purchasedMockInfoRequest.getUserId() != null  ||
				(purchasedMockInfoRequest.getPurchasedMockId() > 0 && purchasedMockInfoRequest.getSubscriptionId() != null)) {

			if (purchasedMockInfoRequest.getPurchasedMockId() > 0)
				mongoTemplate.findAndRemove(Query.query(Criteria.where("purchasedMockId").is(purchasedMockInfoRequest.getPurchasedMockId())), PurchasedMocks.class);
			else
				mongoTemplate.findAndRemove(Query.query(Criteria.where("userId").is(purchasedMockInfoRequest.getUserId()))
						.addCriteria(Criteria.where("subscriptionId").is(purchasedMockInfoRequest.getSubscriptionId())), PurchasedMocks.class);
			
			Users user = mongoTemplate.findOne(Query.query(Criteria.where("userId").is(purchasedMockInfoRequest.getUserId())), Users.class);

			TreeSet<String> userSubscriptionId = user.getSubscriptionIds();
			userSubscriptionId.remove(purchasedMockInfoRequest.getSubscriptionId());

			user.addSubscriptionIds(userSubscriptionId);

			mongoTemplate.save(user, "users");

			RestResponse response = new RestResponse("SUCCESS", "removed the subscription successfully", 200);

			return ResponseEntity.ok(response);
		}else {
			
			RestResponse response = new RestResponse("FAILURE", "missing request body info", 401);

			return ResponseEntity.status(401).body(response);
		}
	}
}