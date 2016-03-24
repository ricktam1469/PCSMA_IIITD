package com.example;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuizResponseRepository extends MongoRepository<QuizResponse, String> {

	public QuizResponse findByquizId(int quizId);
}
