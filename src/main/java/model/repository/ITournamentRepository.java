package model.repository;

import model.tournament.ITournament;

import java.util.List;

public interface ITournamentRepository {
	void addTournament(ITournament tournament);
	void setTournamentToTeam(String team, String tournament);
	List<String> teamsTournaments(String team);
	List<ITournament> allTournaments();
	void removeTeamFromTournament(String team, String tournament);
}