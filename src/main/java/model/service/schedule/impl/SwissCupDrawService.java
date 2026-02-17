package model.service.schedule.impl;

import model.service.schedule.IDrawService;
import model.tournament.ISwissSystemCup;
import model.tournament.ITournament;
import model.tournament.impl.MatchNote;
import model.tournament.regulations.impl.SwissSystemCupRegulations;
import shared.Pair;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SwissCupDrawService implements IDrawService {
	private ISwissSystemCup cup;
	private SwissSystemCupRegulations regulations;
	private List<String> teams;
	private Map<String, List<MatchNote>> leaguePhaseOpponents;
	private Set<Integer> occupiedTeamIndexes;
	private int potSize;
	private int opponentsFromEachPot;

	private void holdTeamLegaueDraw(int teamIndex) {
		for (int tour = 0; tour < regulations.leaguePhaseMatches(); ++tour) {
			if (null != leaguePhaseOpponents.get(teams.get(teamIndex))) {
				continue;
			}

			int opponentIndex;
			do {
				opponentIndex = ThreadLocalRandom.current().nextInt(
						teamIndex + 1, (tour / opponentsFromEachPot + 1) * potSize);
			} while (occupiedTeamIndexes.contains(opponentIndex) &&
					null != leaguePhaseOpponents.get(teams.get(opponentIndex)).get(tour));

			MatchNote match;
			if (0 == tour % 2) {
				match = new MatchNote(teams.get(teamIndex), teams.get(opponentIndex));
			} else {
				match = new MatchNote(teams.get(opponentIndex), teams.get(teamIndex));
			}
			leaguePhaseOpponents.get(teams.get(teamIndex)).add(tour, match);
			leaguePhaseOpponents.get(teams.get(opponentIndex)).add(tour, match);
			occupiedTeamIndexes.add(opponentIndex);
		}
		occupiedTeamIndexes.clear();
	}

	private List<String> winners(List<MatchNote> matches) {
		List<String> res = new ArrayList<>(matches.size());
		for (int i = 0; i < matches.size() - 1; i += 2) {
			short scoreX = (short) (matches.get(i).score().x + matches.get(i + 1).score().y);
			short scoreY = (short) (matches.get(i).score().y + matches.get(i + 1).score().x);
			String winner = scoreX > scoreY ? matches.get(i).homeTeam() : matches.get(i + 1).awayTeam();
			res.add(winner);
		}
		return res;
	}

	private void holdLeagueDraw() {
		regulations = (SwissSystemCupRegulations) cup.regulations();
		potSize = regulations.amountOfTeams() / regulations.pots();
		opponentsFromEachPot = regulations.leaguePhaseMatches() / regulations.pots();
		leaguePhaseOpponents = new TreeMap<>();
		occupiedTeamIndexes = new HashSet<>(regulations.leaguePhaseMatches());
		leaguePhaseOpponents.forEach((_, value) ->
			value = new ArrayList<>(Collections.nCopies(regulations.leaguePhaseMatches(), null))
		);

		for (int i = 0; i < teams.size(); ++i) {
			holdTeamLegaueDraw(i);
		}

		cup.setLeaguePhaseOpponents(leaguePhaseOpponents);
	}

	private void holdIndirectPlayOffDraw() {
		List<Pair<MatchNote>> pairs = new ArrayList<>(regulations.indirectPlayOffClubs());
		occupiedTeamIndexes.clear();

		for (int i = 0; i < regulations.directPlayOffClubs(); ++i) {
			int opponentIndex;
			do {
				opponentIndex = ThreadLocalRandom.current().nextInt(
				2 * regulations.directPlayOffClubs(), regulations.indirectPlayOffClubs() + regulations.directPlayOffClubs());
			} while (occupiedTeamIndexes.contains(opponentIndex));

			MatchNote firstMatch = new MatchNote(teams.get(opponentIndex), teams.get(i));
			MatchNote secondMatch = new MatchNote(teams.get(i), teams.get(opponentIndex));
			pairs.add(new Pair<>(firstMatch, secondMatch));
		}

		cup.setIndirectPlayOffPairs(pairs);
	}

	private void holdPlayOffDraw() {
		List<String> teams = winners(cup.currentStageMatches());
		List<Pair<MatchNote>> nextStage =  new ArrayList<>(teams.size() / 2);
		for (int i = 0; i < teams.size() - 1; i += 2) {
			var firstMatch = new MatchNote(teams.get(i), teams.get(i + 1));
			var secondMatch = new MatchNote(teams.get(i + 1), teams.get(i));
			nextStage.add(new Pair<>(firstMatch, secondMatch));
		}
		cup.setNextStagePairs(nextStage);
	}

	@Override
	public void holdADraw(ITournament tournament) {
		cup = (ISwissSystemCup) tournament;
		teams = cup.teams();

		if (1 == cup.currentTour()) {
			holdLeagueDraw();
		} else if (regulations.leaguePhaseMatches() + 1 == cup.currentTour()) {
			holdIndirectPlayOffDraw();
		} else {
			holdPlayOffDraw();
		}
	}
}