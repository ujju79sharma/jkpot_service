package com.java.jkpot.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DBUtil {

	public static MongoDatabase databaseInfo() {

		MongoClient mongoClient = MongoClients.create("mongodb+srv://jkpot:jkpot@jkpot.uem5j.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
		MongoDatabase db = mongoClient.getDatabase("jkpot");
		return db;
	}
}