package com.java.jkpot.utils;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@Component
public class GoogleFirebaseUtilityDAOImpl implements GoogleFirebaseUtilityDAO {

	private static final String FCMServerKey = "AAAACo-wmg8:APA91bGqULy153omWuUK8eOb5YPph0Pfn-3f2qzfzTBzOn-nc9J1L3fLAyfSNXrXWJvkJwxSYHOtyHv3yv4jr1JSbPkP_zG8A_MbIWB6NF357SXstcCggjIC898M4oQ9U9FTSGsJiO-m";

	@SuppressWarnings("unchecked")
	@Override
	public void sendNotificationToUser(String userName, long senderId, long receiverId, String profilePhoto,
			String receiverFcmToken, String message, String messageBody, String deepLink) {

		try {

			MediaType JSON = MediaType.parse("application/json; charset=utf-8");

			OkHttpClient client = new OkHttpClient();
			/*
			 * for Android
			 */
			JSONObject json = new JSONObject();
			JSONObject dataJson = new JSONObject();

			dataJson.put("title", userName);
			dataJson.put("detail", messageBody);
			dataJson.put("deepLink", deepLink);
			dataJson.put("url", profilePhoto);
			dataJson.put("userId", senderId);
			json.put("to", receiverFcmToken);
			json.put("data", dataJson);

			RequestBody bodyData = RequestBody.create(JSON, json.toString());
			Request request = new Request.Builder().header("Authorization", "key=" + FCMServerKey)
					.url("https://fcm.googleapis.com/fcm/send").post(bodyData).build();

			Response response = client.newCall(request).execute();
			@SuppressWarnings("unused")
			String finalResponse = response.body().string();
			// System.out.println(finalResponse); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}