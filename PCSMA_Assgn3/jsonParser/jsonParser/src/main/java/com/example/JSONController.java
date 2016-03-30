package com.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


import com.mongodb.util.JSON;



@Controller
public class JSONController {
	private int flag1;
	private int flag2;
	private int flag3;
	private int flag4;
	private int a,b,c,d;
	QuizDetails val;
	private int id;
	int countRt=0;
	int Tcount=0;
	private String anyms;
	private String subject;
	private String Email;
	
	@Autowired QuizStudentRepository quizStu;
	@Autowired TeacherDetailsRepository teach;
	@Autowired QuizDetailsRepository qdetails;
	@Autowired QuizResponseRepository quizRes;
	
	
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
	@RequestMapping(value = "/quizdetails", method = RequestMethod.GET)
	public @ResponseBody QuizDetails getAllDetails(
			
			) {
		
				return val;
		//return qdetails.findAll();
		
	}
	
	@RequestMapping(value = "/detailedPerformance", method = RequestMethod.GET)
	public @ResponseBody List<QuizStudent> getStudentDetails(
			
			) {
		
				return quizStu.findAll();
		//return qdetails.findAll();
		
	}
	
	@RequestMapping(value = "/addquizdetails", method = RequestMethod.GET)
	public String addAllDetails(
			@RequestParam("ques")String ques,
			@RequestParam("timer")int timer,
			@RequestParam("answer")String answer,
			@RequestParam("optionA")String optionA,
			@RequestParam("optionB")String optionB,
			@RequestParam("optionC")String optionC,
			@RequestParam("optionD")String optionD,
			@RequestParam(defaultValue="annym")String annym,
			Model model
			) {
		id=0;
		id=(int) qdetails.count();
		
		if(id>0)
		id=qdetails.findAll().get(id-1).getQid();
				
		//val=new QuizDetails();
		//System.out.println("id earliar----->"+id);
		QuizDetails qd=new QuizDetails();
		qd.setQid(id+1);
		qd.setQues(ques);
		qd.setTimer(timer);
		qd.setAnswer(answer);
		//val=qd;
		qdetails.save(qd);
		
		//System.out.println("id----->"+qdetails.count());
		String options="(A) "+optionA+" | "+"(B) "+optionB+" | "+"(C) "+optionC+" | "+"(D) "+optionD;
		
		
		anyms=annym;
		
		return "redirect:/afterwelcome?timer="+timer+"&ques="+ques+"&option="+options;
		
		
	}
@RequestMapping(value="/quizpath", method=RequestMethod.POST)
    
    public @ResponseBody  List<QuizStudent> savePerson(@RequestBody QuizStudent qs){
	
	
	 a=0;b=0;c=0;d=0;
       List<QuizStudent> res = new ArrayList<QuizStudent>();
       System.out.println(qs.getName()+" "+qs.getResponse()+" "+qs.getEmail());
       res.add(qs);
       try{
       QuizStudent qs1=new QuizStudent();
       qs1.setQuizId(id+1);
       qs1.setResponse(qs.getResponse());
       if(anyms.equals("on")){
    	   qs1.setName("Anonymous");
           qs1.setRollnumber("---");
           qs1.setEmail("anonymous@annym.com"+"_"+(id+1));
       
       }
       else {
    	   qs1.setName(qs.getName());
           qs1.setRollnumber(qs.getRollnumber());
           qs1.setEmail(qs.getEmail()+"_"+(id+1));  
       }
       quizStu.save(qs1);
	}
	catch(Exception e)
	{
		
	}
       
       //List<Character> ch=new ArrayList<Character>();
      /* 
       String chr;
       //for(int j=0;j<quizStu.count();j++){
    	//   for(int i=0;i<qs.getResponse().length();i++)
        //   {
        	   //ch.add(qs.getResponse());
        	   chr=qs.getResponse();
        	  // System.out.println(chr+" "+qs.getResponse());
        	   if(chr.equals("A")) a++;
        	   if(chr.equals("B")) b++;
        	   if(chr.equals("C")) c++;
        	   if(chr.equals("D")) d++;
         //  }
      // }
       
      
       QuizResponse qr=new QuizResponse();
       
       qr.setA(a);
       qr.setB(b);
       qr.setC(c);
       qr.setD(d);
       quizRes.save(qr);
       */
       
       //System.out.println(a);
       // return res;
       return res;
       //return quizRes.findAll();
    }

@RequestMapping(value = "/error", method = RequestMethod.GET)
public String error(
	Model model
		) {
	
	subject=" ";
	Email=" ";
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
		
	//subject=sub;
		return "login";
	}
	
	@RequestMapping(value = "/subject", method = RequestMethod.GET)
	public String subject(
			@RequestParam("email")String email,
			
		Model model
			) {
		String str=null;
		if(flag1==1)
		{
			str="subject";
			//flag1=0;
		}
		else
		{
			str="error";
		flag1=0;
		}
		Email=email;	
		try{model.addAttribute("name",teach.findByemail(email).getName());}catch(Exception e){}
		return str;
	}
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome(
			@RequestParam("sub")String sub,
		Model model
			) {
		
	//System.out.println("!!!"+teach.findByemail(email).getName());
		subject=sub;
		model.addAttribute("count",qdetails.count());
		
		System.out.println("CHECK PROBLEM---="+subject+Email);
		
		model.addAttribute("sub",sub);
		try{
			model.addAttribute("name",teach.findByemail(Email).getName());
			model.addAttribute("email",Email);} catch(Exception e){
				return "redirect:/login";
			}
		return "welcome";
	}
	
	@RequestMapping(value = "/afterlogin", method = RequestMethod.GET)
	public String afterLogin(
		@RequestParam("email")String email,
		@RequestParam("password")String password,
		Model model
		
			) {
		String string = null;

		System.out.println(email+teach.exists(email)+" "+teach.exists(password)+" "+password+" "+teach.findBypassword(password).getPassword().equals(password));
		System.out.println(teach.findOne(email).getPassword().equals(password));
		
		try {
			if(teach.exists(email) && teach.findOne(email).getPassword().equals(password)){
				string="redirect:/subject?email="+email;
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
		
		return "redirect:/index";
	
	}
		
		@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(
			Model model
				) {
			
			
			return "register";
		}
		
		@RequestMapping(value = "/afterwelcome", method = RequestMethod.GET)
		public String afterWelcome(
				@RequestParam("timer")int timer,
				@RequestParam("ques")String ques,
				@RequestParam("option")String option,
				Model model
					) {
			
			val=new QuizDetails();
			val.setQues(ques);
			val.setTimer(timer);
		
			model.addAttribute("timer",timer);
			model.addAttribute("ques",ques);
			model.addAttribute("option",option);
			
				return "afterWelcome";
			}
		
		@RequestMapping(value = "/allQuiz", method = RequestMethod.GET)
		public String allQuiz(
				Model model
					) {
			val=null;
			
			model.addAttribute("quizes",qdetails.findAll());
			model.addAttribute("sub",subject);
			try{
			model.addAttribute("name",teach.findByemail(Email).getName());
			model.addAttribute("email",Email);} catch(Exception e){return "redirect:/login";}
				return "allQuiz";
			}
		
		@RequestMapping(value = "/studentresponse", method = RequestMethod.GET)
		public String studentResponse(
				Model model
					) {
				
			model.addAttribute("quizstudent",quizStu.findAll());
			model.addAttribute("sub",subject);
			
			try{
				model.addAttribute("name",teach.findByemail(Email).getName());
				model.addAttribute("email",Email);} catch(Exception e){return "redirect:/login";}
				return "studentResponse";
			}
		
		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public String delete(
			@RequestParam("qid")int qid,	
			Model model,Pageable pageable
				) {

			
			
			System.out.println(qid);
			qdetails.deleteEntry(qid);
			int qsCount=(int) quizStu.count();
			for(int i=0;i<qsCount;i++){
			
				quizStu.deleteEntry(qid);
				
			}
			
			
			//model.addAttribute("fifaUpdate",mongofifa.limitEntry(6));
			
			return "redirect:/allQuiz";
		}
		
		@RequestMapping(value = "/graphresponse", method = RequestMethod.GET)
		public String graphResponse(
				@RequestParam("qid")int qid,
				Model model
					) {
			a=0;b=0;c=0;d=0;
			List<Character> ch=new ArrayList<Character>();
		    List<QuizStudent> qs=new ArrayList<QuizStudent>();   
		    String chr;
		    
		    for(int j=0;j<quizStu.count();j++){
		    	
		    	qs.addAll(quizStu.findAll());
		    	try{
		    		if(qs.get(j).getQuizId()==qid){
		    	chr=qs.get(j).getResponse();
		    	
		   
		          	   //ch.add(qs.getResponse());
		        	  // chr=qs.getResponse();
		        	  // System.out.println(chr);
		        	  if(chr.equals("A")) a++;
		        	  if(chr.equals("B")) b++;
		        	  if(chr.equals("C")) c++;
		        	  if(chr.equals("D")) d++;
		    		}
		           }
		    	catch(Exception e){
		    		
		    	}
		    	
		       }			
			
			int[] intArray = new int[] {a,b,c,d};
			model.addAttribute("message",intArray);
            
			model.addAttribute("sub",subject);
			
			try{
				model.addAttribute("name",teach.findByemail(Email).getName());
				model.addAttribute("email",Email);} catch(Exception e){return "redirect:/login";}
			
				return "graphResponse";
			}
		
		@RequestMapping(value = "/performance", method = RequestMethod.GET)
		public String performance(
				
				Model model
					) {
			
			List<QuizDetails> qd=new ArrayList<QuizDetails>();
			qd.addAll(qdetails.findAll());
			
			List<String> qname=new ArrayList<String>();
			List<Integer> val=new ArrayList<Integer>();
			List<Integer> val1=new ArrayList<Integer>();
			List<Integer> val2=new ArrayList<Integer>();
			
				
			for(int i=0;i<qd.size();i++){
				if(qd.get(i).getTotalStudent()!=0)
				
					qname.add("Quiz # "+qd.get(i).getQid()+"("+(double)( (qd.get(i).getCorrectAns())/(qd.get(i).getTotalStudent()) ) *100+" %)");
				else
					qname.add("Quiz # "+qd.get(i).getQid());

			
			}
			
			for(int i=0;i<qd.size();i++){
				//val.add( ( (qd.get(i).getCorrectAns())/(qd.get(i).getTotalStudent()) ) *100);
			}
			
			
			
			for(int i=0;i<qd.size();i++){
				val1.add(qd.get(i).getCorrectAns());
			}
			for(int i=0;i<qd.size();i++){
				val2.add(qd.get(i).getTotalStudent()-qd.get(i).getCorrectAns());
			}
			
			
			model.addAttribute("quizName",qname.toArray());
			model.addAttribute("val1",val1);
			model.addAttribute("val2",val2);
			
		
			
            model.addAttribute("sub",subject);
			
			try{
				model.addAttribute("name",teach.findByemail(Email).getName());
				model.addAttribute("email",Email);} catch(Exception e){return "redirect:/login";}
			
				return "performance";
			}
		
		@RequestMapping(value = "/answerpublish", method = RequestMethod.GET)
		public String getAnswerDetails(
				@RequestParam("qid")int qid,
				@RequestParam("answer")String answer,
				Model model
				) {
			
			QuizResponse qr=new QuizResponse();
	          qr.setQuizId(qid);
		      qr.setAnswer(answer);
		       quizRes.save(qr);
		      
		       QuizDetails qds=new QuizDetails();
		       
		       List<QuizDetails> qd=new ArrayList<QuizDetails>();  
		       qd.addAll(qdetails.findAll());
		       //System.out.println("QID--"+qid+"ID"+qd.get(qid));
		       int x=0;
				
		       for(int i=0;i<qdetails.count();i++){
		        if(qd.get(i).getQid()==qid)
		        	x=i;
		       }
		       System.out.println("x--->"+x);
		    	qd.get(x).setAnswer(answer);  
		      
		    	
		    	
		    	List<QuizStudent> qs=new ArrayList<QuizStudent>();
		    	qs.addAll(quizStu.findAll());
		    	
		    	
		    	for(int j=0;j<qs.size();j++){
		    		
		    		if(qs.get(j).getQuizId()==qid){
		    			if(qs.get(j).getResponse().equals(answer)){
		    				countRt++;
		    				qs.get(j).setResult(true);
		    			}
		    			else
		    			{
		    				qs.get(j).setResult(false);
		    			}
		    			Tcount++;
		    		}
		    	}
		    	String crctAnswer="Correctly Answered: "+countRt+"\n Student Appeared: "+Tcount;
		    	qd.get(x).setCorrectStudent(crctAnswer);
		    	
		    	qd.get(x).setCorrectAns(countRt);
		    	qd.get(x).setTotalStudent(Tcount);
		    	
		    	quizStu.save(qs);
		    	qdetails.save(qd);
		    	
		    	countRt=0;
		    	Tcount=0;
		    	
		    	
		    	//List<QuizStudent> qs=new ArrayList<QuizStudent>();
		    	
		    	
		       return "redirect:/allQuiz";
			//return qdetails.findAll();
			
		}
		
		
		
}
