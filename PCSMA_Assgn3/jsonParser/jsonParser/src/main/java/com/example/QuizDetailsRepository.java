package com.example;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuizDetailsRepository extends MongoRepository<QuizDetails, String> ,QuizDetailsRepositoryCustom{
	
	public QuizDetails findByqid(int qid);

}
