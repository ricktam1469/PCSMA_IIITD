package com.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class JSONController {
	private int flag1;
	private int flag2;
	private int flag3;
	private int flag4;
	
	
	
	@Autowired QuizStudentRepository quizStu;
	@Autowired TeacherDetailsRepository teach;
	
	/*@RequestMapping(value="/jsonPath", method=RequestMethod.POST)
    
    public @ResponseBody String savePerson(@RequestParam("name") String name){
       List<QuizStudent> res = new ArrayList<QuizStudent>();
       // for (QuizStudent person: wrapper.getQs()){
       // qservice.save(person);
         //res.add(name);
        // res.add(roll);
         //res.add(response);
       //res.add(qs);
    //mongo.save(res);
        return name;
    }*/
	
	
@RequestMapping(value="/jsonPath", method=RequestMethod.POST)
    
    public @ResponseBody List<QuizStudent> savePerson(@RequestBody QuizStudent qs){
	
	//  a=0;b=0;c=0;d=0;
       List<QuizStudent> res = new ArrayList<QuizStudent>();
       System.out.println(qs.getName());
       res.add(qs);
       quizStu.save(res);
       
       /*List<Character> ch=new ArrayList<Character>();
       
       char chr;
       for(int j=0;j<quizStu.count();j++){
    	   for(int i=0;i<qs.getResponse().length();i++)
           {
        	   ch.add(qs.getResponse().charAt(i));
        	   chr=qs.getResponse().charAt(i);
        	  // System.out.println(chr+" "+qs.getResponse());
        	   if(chr=='a') a++;
        	   if(chr=='b') b++;
        	   if(chr=='c') c++;
        	   if(chr=='d') d++;
           }
       }
       
       
       QuizResponse qr=new QuizResponse();
       
       qr.setA(a);
       qr.setB(b);
       qr.setC(c);
       qr.setD(d);
       quizRes.save(qr);
       */
       
       //System.out.println(a);
        return res;
       //return quizRes.findAll();
    }

@RequestMapping(value = "/error", method = RequestMethod.GET)
public String error(
	Model model
		) {
	
			
	return "error";
}


	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(
		Model model
			) {
		
				
		model.addAttribute("json",teach.findAll());
		
		return "index";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(
		Model model
			) {
		
	
		return "login";
	}
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome(
		Model model
			) {
		String str=null;
		if(flag1==1)
		{
			str="welcome";
			flag1=0;
		}
		else
			str="error";
		flag1=0;
		return str;
	}
	
	@RequestMapping(value = "/afterlogin", method = RequestMethod.GET)
	public String afterLogin(
		@RequestParam("email")String email,
		@RequestParam("password")String password,
		Model model
		
			) {
		String string = null;

		//System.out.println(teach.exists(email)+" "+teach.exists(password)+" "+password+" "+teach.findBypassword(password).getPassword().equals(password));
		//System.out.println(teach.findOne(email).getPassword().equals(password));
		
		try {
			if(teach.exists(email) && teach.findOne(email).getPassword().equals(password)){
				string="redirect:/welcome";
				flag1=1;
			}
				
			else{
				string="redirect:/login";
				flag1=0;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			string="redirect:/login";
		}
		return string;
	
	}
	@RequestMapping(value = "/registermethod", method = RequestMethod.GET)
	public String registerUser(
		@RequestParam("name")String name,
		@RequestParam("email")String email,
		@RequestParam("password")String password,
		@RequestParam("dob")String dob,
		Model model
		
			) {
				
		TeacherDetails td=new TeacherDetails();
		td.setName(name);
		td.setEmail(email);
		td.setPassword(password);
		td.setDob(dob);
		
		teach.save(td);
		
		return "redirect:/login";
	
	}
		
		@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(
			Model model
				) {
			
			
			return "register";
		}
}
