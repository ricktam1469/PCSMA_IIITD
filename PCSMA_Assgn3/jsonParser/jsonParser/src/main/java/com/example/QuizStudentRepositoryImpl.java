package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class QuizStudentRepositoryImpl implements QuizDetailsRepositoryCustom {
	@Autowired private MongoTemplate mongotemp;
	@Autowired QuizStudentRepository qs;
	@Override
	public void deleteEntry(int qid) {
		// TODO Auto-generated method stub
		//System.out.println("==="+qs.findByquizId(qid).getName());
		try{
		mongotemp.remove(qs.findByquizId(qid));
		}
		catch(Exception e){
			
		}
		 //mongotemp.find(new Query().with(new Sort(new Order(Direction.DESC,"date"))).limit(val), FifaUpdate.class);
		 //mongotemp.remove(new Query()., entityClass)

  }
}
