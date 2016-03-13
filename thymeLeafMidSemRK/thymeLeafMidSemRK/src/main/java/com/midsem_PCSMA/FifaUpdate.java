package com.midsem_PCSMA;

import org.springframework.data.annotation.Id;

public class FifaUpdate {
	

	private String club;
	private String team1;	
	private int team1Goals;
	private String team2;
	private int team2Goals;
	
	private String winner;
	@Id
	private String date;
	private String playerOfMatch;
	
	public String getClub() {
		return club;
	}
	public void setClub(String club) {
		this.club = club;
	}
	
	public int getTeam1Goals() {
		return team1Goals;
	}
	public void setTeam1Goals(int team1Goals) {
		this.team1Goals = team1Goals;
	}
	public int getTeam2Goals() {
		return team2Goals;
	}
	public void setTeam2Goals(int team2Goals) {
		this.team2Goals = team2Goals;
	}
	public String getPlayerOfMatch() {
		return playerOfMatch;
	}
	public void setPlayerOfMatch(String playerOfMatch) {
		this.playerOfMatch = playerOfMatch;
	}
	public String getTeam1() {
		return team1;
	}
	public void setTeam1(String team1) {
		this.team1 = team1;
	}
	public String getTeam2() {
		return team2;
	}
	public void setTeam2(String team2) {
		this.team2 = team2;
	}
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	             

}
