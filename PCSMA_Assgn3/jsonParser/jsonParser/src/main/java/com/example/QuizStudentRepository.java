package com.example;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface QuizStudentRepository extends MongoRepository<QuizStudent, String>, QuizStudentRepositoryCustom 
{	
	public QuizStudent findByemail(String email);
	public QuizStudent findByquizId(int quizId);

}
