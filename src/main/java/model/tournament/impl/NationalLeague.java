package model.tournament.impl;

import model.tournament.regulations.IRegulations;
import model.tournament.regulations.impl.LeagueRegulations;
import model.tournament.INationalLeague;

import java.security.InvalidParameterException;
import java.util.*;

public class NationalLeague implements INationalLeague {
	private String name;
	private ArrayList<TournamentTableNote> table;
	private LeagueRegulations regulations;
	private Map<String, List<MatchNote>> schedule;
	private short currentTour = 1;


	public NationalLeague(String name, LeagueRegulations regulations) throws InvalidParameterException {
		if (0 != regulations.amountOfTeams() % 2) {
			throw new InvalidParameterException("amount of teams must be even (league '" + name + "')");
		}
		this.name = name;
		this.regulations = regulations;
		table = new ArrayList<>(this.regulations.amountOfTeams());
	}

	@Override
	public List<TournamentTableNote> tournamentTable() {
		return table;
	}

	@Override
	public IRegulations regulations() {
		return regulations;
	}

	@Override
	public List<MatchNote> nextStageMatches() {
		List<MatchNote> res = new ArrayList<>(regulations.amountOfTeams() / 2);
		schedule.forEach((key, value) -> res.add(value.get(currentTour - 1)));
		return res;
	}

	@Override
	public List<MatchNote> allTeamMatches(String team) {
		return schedule.get(team);
	}

	@Override
	public void addTeam(String team) {
		if (table.size() == regulations.amountOfTeams()) {
			throw new IndexOutOfBoundsException("league is already full");
		}

		table.add(new TournamentTableNote(team));
	}

	@Override
	public void removeTeam(String team) {
		table.removeIf(t -> t.team().equals(team));
	}

	@Override
	public void setSchedule(Map<String, List<MatchNote>> schedule) {
		this.schedule = schedule;
	}

	@Override
	public short currentTour() {
		return currentTour;
	}

	@Override
	public void increaseTour() {
		currentTour += 1;
	}

	@Override
	public void resetResults() {
		table.forEach(t -> t.reset());
		table.sort(Comparator.comparing(TournamentTableNote::team));
	}

	@Override
	public void replaceTeam(String oldTeam, String newTeam) {
		if (!schedule.isEmpty()) {
			throw new IllegalStateException("tournament has been already started (league - '" + name + "', replaceTeam)");
		}
		int index = table.indexOf(oldTeam);
		table.set(index, new TournamentTableNote(newTeam));
		table.sort(Comparator.comparing(TournamentTableNote::team));
	}

	@Override
	public List<String> teams() {
		List<String> res = new ArrayList<>(regulations.amountOfTeams());
		table.forEach(note -> res.add(note.team()));
		return res;
	}

	@Override
	public String name() {
		return name;
	}
}