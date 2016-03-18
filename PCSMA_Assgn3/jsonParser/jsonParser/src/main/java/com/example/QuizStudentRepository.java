package com.example;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface QuizStudentRepository extends MongoRepository<QuizStudent, String> {
	
	public QuizStudent findByrollnumber(String rollnumber);

}
