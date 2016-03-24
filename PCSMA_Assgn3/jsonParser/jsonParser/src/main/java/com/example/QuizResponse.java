package com.example;

import org.springframework.data.annotation.Id;

public class QuizResponse {
	@Id
	private int quizId;
	private String answer;
	public int getQuizId() {
		return quizId;
	}
	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	

}
