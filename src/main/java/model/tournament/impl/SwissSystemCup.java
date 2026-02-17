package model.tournament.impl;

import model.tournament.regulations.IRegulations;
import model.tournament.regulations.impl.SwissSystemCupRegulations;
import model.tournament.ISwissSystemCup;
import shared.IsPowerOfTwo;
import shared.Pair;

import java.security.InvalidParameterException;
import java.util.*;

public class SwissSystemCup implements ISwissSystemCup {
	private final String name;
	private List<TournamentTableNote> leagueTable;
	private short currentTour;
	private List<Pair<MatchNote>> indirectPlayOffPairs;
	private List<List<Pair<MatchNote>>> playOffPairs;
	private Map<String, List<MatchNote>> leaguePhaseOpponents;
	private SwissSystemCupRegulations regulations;


	public SwissSystemCup(String name, SwissSystemCupRegulations regulations, List<String> teams) {
		if (regulations.amountOfTeams() != teams.size() ||
				regulations.directPlayOffClubs() + regulations.indirectPlayOffClubs() > regulations.amountOfTeams()) {
			throw new InvalidParameterException(
					"number of teams in regulations and in reality are different (cup '" + name + "')");
		}
		if (!IsPowerOfTwo.check(regulations.directPlayOffClubs() + regulations.indirectPlayOffClubs() / 2)) {
			throw new InvalidParameterException("amount of teams in cup must be power of 2 (cup '" + name + "')");
		}
		if (regulations().amountOfTeams() % regulations.pots() != 0) {
			throw new InvalidParameterException("amount of teams in cup must be multiple of amount of pots (cup '" + name + "')");
		}
		this.name = name;
		this.regulations = regulations;
		leagueTable = new ArrayList<>(this.regulations.amountOfTeams());
		playOffPairs = new ArrayList<>((int) (Math.log(regulations.directPlayOffClubs()) / Math.log(2)));
		teams.forEach(t -> leagueTable.add(new TournamentTableNote(t)));
	}

	@Override
	public void resetResults() {
		leagueTable.clear();
		leaguePhaseOpponents.clear();
		indirectPlayOffPairs.clear();
		playOffPairs.clear();
	}

	@Override
	public void replaceTeam(String oldTeam, String newTeam) {
		if (!leaguePhaseOpponents.isEmpty()) {
			throw new IllegalStateException("tournament has been already started (cup - '" + name + "', replaceTeam)");
		}
		int index = leagueTable.indexOf(oldTeam);
		leagueTable.set(index, new TournamentTableNote(newTeam));
		leagueTable.sort(Comparator.comparing(TournamentTableNote::team));
	}

	@Override
	public List<String> teams() {
		List<String> res = new ArrayList<>(regulations.amountOfTeams());
		leagueTable.forEach(note -> res.add(note.team()));
		return res;
	}

	@Override
	public IRegulations regulations() {
		return regulations;
	}

	@Override
	public List<MatchNote> nextStageMatches() {
		if (currentTour < regulations.leaguePhaseMatches()) {
			List<MatchNote> res = new ArrayList<>(regulations.amountOfTeams() / 2);
			leaguePhaseOpponents.forEach((_, value) -> res.add(value.get(currentTour)));
			return res;
		}
		if (regulations.leaguePhaseMatches() <= currentTour && regulations.leaguePhaseMatches() + 2 > currentTour) {
			List<MatchNote> res = new ArrayList<>(regulations.indirectPlayOffClubs() / 2);
			indirectPlayOffPairs.forEach(pair -> {
				MatchNote match = currentTour - regulations.leaguePhaseMatches() == 0 ? pair.x : pair.y;
				res.add(match);
			});
			return res;
		}
		List<MatchNote> res = new ArrayList<>(playOffPairs.get(currentTour - regulations.leaguePhaseMatches() - 2).size());
		playOffPairs.get(currentTour - regulations.leaguePhaseMatches() - 2).forEach(pair -> {
			MatchNote match = currentTour - regulations.leaguePhaseMatches() % 2 == 0 ? pair.x : pair.y;
			res.add(match);
		});
		return res;
	}

	@Override
	public List<MatchNote> allTeamMatches(String team) {
		if (currentTour < regulations.leaguePhaseMatches()) {
			return leaguePhaseOpponents.get(team);
		}

		List<Pair<MatchNote>> stage;
		if (regulations.leaguePhaseMatches() <= currentTour && regulations.leaguePhaseMatches() + 2 > currentTour) {
			stage = indirectPlayOffPairs;
		} else {
			stage = playOffPairs.get(currentTour - regulations.leaguePhaseMatches() - 2);
		}

		List<MatchNote> res = new ArrayList<>(2);
		var pair = stage.stream().filter(p -> p.x.containsTeam(team)).findAny().get();
		res.add(pair.x);
		res.add(pair.y);
		return res;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public List<TournamentTableNote> leaguePhaseTable() {
		return leagueTable;
	}

	@Override
	public void setTeams(List<String> teams) {
		if (teams.size() != regulations.amountOfTeams()) {
			throw new InvalidParameterException("invalid number of teams (cup - '" + name + "', setTeams)");
		}
		var newTable = new ArrayList<TournamentTableNote>(regulations.amountOfTeams());
		teams.forEach(t -> newTable.add(new TournamentTableNote(t)));
		leagueTable = newTable;
	}

	@Override
	public void setLeaguePhaseOpponents(Map<String, List<MatchNote>> opponents) {
		leaguePhaseOpponents = opponents;
	}

	@Override
	public void setIndirectPlayOffPairs(List<Pair<MatchNote>> pairs) {
		indirectPlayOffPairs = pairs;
	}

	@Override
	public void setNextStagePairs(List<Pair<MatchNote>> pairs) {
		playOffPairs.add(pairs);
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
	public List<MatchNote> teamLeaguePhaseOpponents(String team) {
		return leaguePhaseOpponents.get(team);
	}

	@Override
	public Map<String, List<MatchNote>> allTeamsLeaguePhaseOpponents() {
		return leaguePhaseOpponents;
	}

	@Override
	public List<Pair<MatchNote>> indirectPlayOffPairs() {
		return indirectPlayOffPairs;
	}

	@Override
	public List<Pair<MatchNote>> currentStagePairs() {
		return playOffPairs.get(currentTour - regulations.leaguePhaseMatches() - 2);
	}

	@Override
	public List<MatchNote> currentStageMatches() {
		if (currentTour < regulations.leaguePhaseMatches()) {
			List<MatchNote> res = new ArrayList<>(regulations.amountOfTeams() / 2);
			leaguePhaseOpponents.forEach((_, value) ->
				res.add(value.get(currentTour)));
			return res;
		}
		if (currentTour >= regulations.leaguePhaseMatches() && currentTour < regulations.leaguePhaseMatches() + 2) {
			List<MatchNote> res = new ArrayList<>(regulations.indirectPlayOffClubs() / 2);
			indirectPlayOffPairs.forEach(pair -> {
				MatchNote match = currentTour - regulations.leaguePhaseMatches() == 0 ? pair.x : pair.y;
				res.add(match);
			});
			return res;
		}

		List<MatchNote> res = new ArrayList<>(playOffPairs.get(currentTour - regulations.leaguePhaseMatches() - 2).size());
		playOffPairs.get(currentTour - regulations.leaguePhaseMatches() - 2).forEach(pair -> {
			MatchNote match = currentTour - regulations.leaguePhaseMatches() % 2 == 0 ? pair.x : pair.y;
			res.add(match);
		});
		return res;
	}
}