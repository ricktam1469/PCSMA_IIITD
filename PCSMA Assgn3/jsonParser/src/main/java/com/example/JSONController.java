package com.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.util.JSON;



@Controller
public class JSONController {
	
	@Autowired MongoRepository mongo;
	
	@RequestMapping(value="/jsonPath", method=RequestMethod.POST , headers = "Content-Type: application/json")
    @ResponseBody
    public List<QuizStudent> savePerson(@RequestBody QuizStudent qs ) {
       List<QuizStudent> res = new ArrayList<QuizStudent>();
       // for (QuizStudent person: wrapper.getQs()){
       // qservice.save(person);
         //res.add(name);
        // res.add(roll);
         //res.add(response);
       res.add(qs);
    mongo.save(res);
        return res;
    }
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(
		Model model
			) {
		
				
		model.addAttribute("json",mongo.findAll());
		
		return "index";
	}

}
