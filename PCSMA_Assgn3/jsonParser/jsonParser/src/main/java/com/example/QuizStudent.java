package com.example;

import org.springframework.data.annotation.Id;

public class QuizStudent {
	
	private int quizId;
	
	public int getQuizId() {
		return quizId;
	}
	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}
	
	
	private String name;
	
	
	private String rollnumber;	
	
	private String response;
	
	@Id
	private String email;
	
	private boolean result;
	
	
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRollnumber() {
		return rollnumber;
	}
	public void setRollnumber(String rollnumber) {
		this.rollnumber = rollnumber;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	

}
