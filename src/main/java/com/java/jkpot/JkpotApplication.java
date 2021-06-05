package com.java.jkpot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@SpringBootApplication
public class JkpotApplication {

	public static void main(String[] args) {
		SpringApplication.run(JkpotApplication.class, args);
	}
//
//	@Bean
//	public MongoClient getMongoConnectivity() {
//
//		String url1 = "mongodb+srv://jkpot:jkpot@cluster0.vkskw.mongodb.net/jkpot?retryWrites=true&w=majority";
//
//		MongoClient mongoClient = MongoClients.create(url1);
//
//		return mongoClient;
//	}
}