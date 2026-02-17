package model.service.schedule.impl;

import model.service.schedule.IScheduleService;
import model.tournament.ITournament;

import java.util.List;
import java.util.Map;

public class ScheduleService implements IScheduleService {
	private Map<String, List<ITournament>> tournamentsByTeamName;

	@Override
	public void addTournamentForTeam(String teamName, ITournament tournament) {

	}

	@Override
	public void removeTeamFromTournament(String teamName, String tournamentName) {

	}

	@Override
	public List<ITournament> allTeamsTournaments(String teamName) {
		return List.of();
	}
}