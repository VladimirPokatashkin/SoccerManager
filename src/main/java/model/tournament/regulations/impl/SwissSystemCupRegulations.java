package model.tournament.regulations.impl;


import model.tournament.regulations.IRegulations;

public class SwissSystemCupRegulations implements IRegulations {
	private short leaguePhaseMembers;
	private short leaguePhaseMatches;
	private short pots;
	private short directPlayOffClubs;
	private short indirectPlayOffClubs;



	public SwissSystemCupRegulations(short leaguePhaseMembers, short leaguePhaseMatches, short pots,
	short directPlayOffClubs, short indirectPlayOffClubs) {
		this.leaguePhaseMembers = leaguePhaseMembers;
		this.leaguePhaseMatches = leaguePhaseMatches;
		this.pots = pots;
		this.directPlayOffClubs = directPlayOffClubs;
		this.indirectPlayOffClubs = indirectPlayOffClubs;
	}

	@Override
	public short amountOfTeams() {
		return leaguePhaseMembers;
	}

	public short leaguePhaseMatches() {
		return leaguePhaseMatches;
	}

	public short pots() {
		return pots;
	}

	public short directPlayOffClubs() {
		return directPlayOffClubs;
	}

	public short indirectPlayOffClubs() {
		return indirectPlayOffClubs;
	}

	public short playOfParticipantsNumber() {
		return (short) (directPlayOffClubs + indirectPlayOffClubs / 2);
	}
}