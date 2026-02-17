package model.tournament.impl;

import shared.Pair;

import java.security.InvalidParameterException;
import java.time.LocalDate;

public class MatchNote {
	private String homeTeam;
	private String awayTeam;
	private short goalsTeamX;
	private short goalsTeamY;
	private LocalDate date;

	public MatchNote(String homeTeam, String awayTeam, LocalDate date) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.date = date;
	}

	public MatchNote(String homeTeam, String awayTeam) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}

	public Pair<Short> score() {
		return new Pair<>(goalsTeamX, goalsTeamY);
	}

	public void addTeamXGoals(short goals) {
		goalsTeamX += goals;
	}

	public void addTeamYGoals(short goals) {
		goalsTeamY += goals;
	}

	public String homeTeam() {
		return homeTeam;
	}

	public String awayTeam() {
		return awayTeam;
	}

	public void changeDate(LocalDate newDate) {
		date = newDate;
	}

	public LocalDate date() {
		return date;
	}

	public boolean containsTeam(String team) {
		return homeTeam.equals(team) || awayTeam.equals(team);
	}

	public String teamOpponent(String team) {
		if (!containsTeam(team)) {
			throw new InvalidParameterException(
					"team '" + team +"' does not participate in this match (MatchNote::teamOpponent)");
		}
		if (awayTeam.equals(team)) {
			return homeTeam;
		}
		return awayTeam;
	}
}