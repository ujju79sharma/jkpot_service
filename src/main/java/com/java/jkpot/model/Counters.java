package com.java.jkpot.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "counters")
public class Counters {

	private String name;
	private long sequence;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	@Override
	public String toString() {
		return "Counters [name=" + name + ", sequence=" + sequence + "]";
	}
}