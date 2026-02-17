package model.repository.impl;

import model.repository.ITournamentRepository;
import model.tournament.ITournament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TournamentRepository implements ITournamentRepository {
	private Map<String, ITournament> tournaments;
	private Map<String, List<String>> teamsAndTournamentsMap;


	public TournamentRepository() {
		tournaments = new HashMap<>();
		teamsAndTournamentsMap = new HashMap<>();
	}

	@Override
	public void addTournament(ITournament tournament) {
		tournaments.put(tournament.name(), tournament);
	}

	@Override
	public void setTournamentToTeam(String team, String tournament) {
		if (!teamsAndTournamentsMap.containsKey(team)) {
			teamsAndTournamentsMap.put(team, new ArrayList<>());
		}
		teamsAndTournamentsMap.get(team).add(tournament);
	}

	@Override
	public List<String> teamsTournaments(String team) {
		return teamsAndTournamentsMap.get(team);
	}

	@Override
	public List<ITournament> allTournaments() {
		return List.of((ITournament) tournaments.values());
	}

	@Override
	public void removeTeamFromTournament(String team, String tournament) {
		var list = teamsAndTournamentsMap.get(team);
		if (null == list) {
			throw new IllegalArgumentException("team '" + team + "' was not found (removeTeamFromTournament)");
		}
		list.remove(tournament);
	}
}