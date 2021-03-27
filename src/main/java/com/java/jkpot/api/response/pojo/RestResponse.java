package com.java.jkpot.api.response.pojo;

public class RestResponse {

	private String message;
	private Object data;
	private int statusCode;

	public RestResponse(String message, Object data, int statusCode) {
		super();
		this.message = message;
		this.data = data;
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}