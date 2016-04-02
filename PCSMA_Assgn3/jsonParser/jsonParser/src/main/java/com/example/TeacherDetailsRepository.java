package com.example;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeacherDetailsRepository extends MongoRepository<TeacherDetails, String>
{
	public TeacherDetails findByemail(String email);
	public TeacherDetails findBypassword(String password);
}
