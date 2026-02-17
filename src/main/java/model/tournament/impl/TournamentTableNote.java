package model.tournament.impl;

public class TournamentTableNote {
	private String teamName;
	private short matches;
	private short points;
	private short goalsConceded;
	private short goalsScored;
	private short cleanSheets;

	public TournamentTableNote(String teamName) {
		this.teamName = teamName;
	}

	public String team() {
		return teamName;
	}

	public short matches() {
		return matches;
	}

	public void increaseMatches() {
		matches += 1;
	}

	public short points() {
		return points;
	}

	public void increasePoints(short add) {
		points += add;
	}

	public short goalsConceded() {
		return goalsConceded;
	}

	public void increaseGoalsConceded(short add) {
		goalsConceded += add;
	}

	public short goalsScored() {
		return goalsScored;
	}

	public void increaseGoalScored(short add) {
		goalsScored += add;
	}

	public short cleanSheets() {
		return cleanSheets;
	}

	public void increaseCleanSheets() {
		cleanSheets += 1;
	}

	public void reset() {
		matches = 0;
		points = 0;
		goalsConceded = 0;
		goalsScored = 0;
		cleanSheets = 0;
	}
}