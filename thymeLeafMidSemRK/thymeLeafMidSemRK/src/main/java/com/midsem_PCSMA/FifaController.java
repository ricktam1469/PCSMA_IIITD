package com.midsem_PCSMA;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FifaController {
	
@Autowired MongoRepository mongo;
	
	
	@RequestMapping(value = "/allDetails", method = RequestMethod.GET)
	public @ResponseBody List<FifaUpdate> getAllUser(
			) {
		
		return mongo.findAll();
		
	}
	
	@RequestMapping(value = "/addDetails", method = RequestMethod.GET)
	public String getAllUser(
		@RequestParam("club")String club,
		@RequestParam("team1")String team1,
		@RequestParam("team1Goals")int team1Goals,
		@RequestParam("team2")String team2,
		@RequestParam("team2Goals")int team2Goals,
		@RequestParam("winner")String winner,
		@RequestParam("date")String date,
		@RequestParam("playerOfMatch")String playerOfMatch,
		Model model
		
			) {
				
		FifaUpdate fifa = new FifaUpdate();
		
		fifa.setClub(club);
		fifa.setTeam1(team1);
		fifa.setTeam1Goals(team1Goals);
		fifa.setTeam2(team2);
		fifa.setTeam2Goals(team2Goals);
		fifa.setWinner(winner);
		fifa.setDate(date);
		fifa.setPlayerOfMatch(playerOfMatch);
		
		mongo.save(fifa);
		
		//return mongo.findAll();
		return "redirect:/index";
		
	}
	
	/*@RequestMapping(value = "/addDetails", method = RequestMethod.GET)
	public List<FifaUpdate) getAllUser(
		@RequestParam("club")String club,
		@RequestParam("team1")String team1,
		@RequestParam("team1Goals")int team1Goals,
		@RequestParam("team2")String team2,
		@RequestParam("team2Goals")int team2Goals,
		@RequestParam("winner")String winner,
		@RequestParam("date")String date,
		@RequestParam("playerOfMatch")String playerOfMatch
		
		
			) {
				
		FifaUpdate fifa = new FifaUpdate();
		
		fifa.setClub(club);
		fifa.setTeam1(team1);
		fifa.setTeam1Goals(team1Goals);
		fifa.setTeam2(team2);
		fifa.setTeam2Goals(team2Goals);
		fifa.setWinner(winner);
		fifa.setDate(date);
		fifa.setPlayerOfMatch(playerOfMatch);
		
		mongo.save(fifa);
		
		return mongo.findAll();
		
		
	}*/
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(
		Model model,Pageable pageable
			) {
		
		Query q=new Query();
		q.limit(2);
		
		
		model.addAttribute("fifaUpdate",mongo.findAll(pageable));
		
		return "index";
	}
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String Insert(
		Model model
			) {
		
		
		
		//model.addAttribute("students",mongo.findAll());
		return "Insert";
	}
}
