package com.java.jkpot.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DBUtil {

	public static MongoDatabase databaseInfo() {

		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/?readPreference=primary&ssl=false");
		MongoDatabase db = mongoClient.getDatabase("jkpot");
		return db;
	}
}