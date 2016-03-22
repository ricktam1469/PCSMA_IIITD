package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class QuizDetailsRepositoryImpl implements QuizDetailsRepositoryCustom {
	@Autowired private MongoTemplate mongotemp;
	@Autowired QuizDetailsRepository qd;
	@Override
	public void deleteEntry(int qid) {
		// TODO Auto-generated method stub
		System.out.println(qd.findByqid(qid));
		mongotemp.remove(qd.findByqid(qid));
		
	}

}
