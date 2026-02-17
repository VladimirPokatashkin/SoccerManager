package model.tournament.regulations.impl;

import model.tournament.regulations.IRegulations;

public class LeagueRegulations implements IRegulations {
	private final short _amountOfTeams;
	private final short _teamsToPromote;
	private final short _teamsToEliminate;


	public LeagueRegulations (short amountOfTeams, short teamsToPromote, short teamsToEliminate) {
		_amountOfTeams = amountOfTeams;
		_teamsToPromote = teamsToPromote;
		_teamsToEliminate = teamsToEliminate;
	}


	public short amountOfTeams() {
		return _amountOfTeams;
	}

	public short teamsToPromote() {
		return _teamsToPromote;
	}

	public short teamsToEliminate() {
		return _teamsToEliminate;
	}
}