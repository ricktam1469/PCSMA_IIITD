package com.example;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface JSONRepo extends MongoRepository<QuizStudent, String> {
	
	public QuizStudent findByname(String name);

}
