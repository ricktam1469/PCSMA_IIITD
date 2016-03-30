package com.example;

import org.springframework.data.annotation.Id;

public class QuizDetails {
	
	@Id
	private int qid;
	private String ques;
	private int timer;
	private String answer;
	private String correctStudent;
	private int correctAns;
	private int totalStudent;

	public int getCorrectAns() {
		return correctAns;
	}
	public void setCorrectAns(int correctAns) {
		this.correctAns = correctAns;
	}
	public int getTotalStudent() {
		return totalStudent;
	}
	public void setTotalStudent(int totalStudent) {
		this.totalStudent = totalStudent;
	}
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	public String getQues() {
		return ques;
	}
	public String getCorrectStudent() {
		return correctStudent;
	}
	public void setCorrectStudent(String correctStudent) {
		this.correctStudent = correctStudent;
	}
	public void setQues(String ques) {
		this.ques = ques;
	}
	public int getTimer() {
		return timer;
	}
	public void setTimer(int timer) {
		this.timer = timer;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
