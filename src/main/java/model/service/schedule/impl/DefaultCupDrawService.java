package model.service.schedule.impl;

import model.service.schedule.IDrawService;
import model.tournament.ICup;
import model.tournament.ITournament;
import model.tournament.impl.MatchNote;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class DefaultCupDrawService implements IDrawService {
	private List<String> winners(List<MatchNote> matches) {
		List<String> res = new ArrayList<>(matches.size());
		matches.forEach(match -> {
			String winner = match.score().x > match.score().y ? match.homeTeam() : match.awayTeam();
			res.add(winner);
		});
		return res;
	}

	@Override
	public void holdADraw(ITournament tournament) {
		List<String> teams = winners(tournament.currentStageMatches());
		int size = teams.size();
		List<MatchNote> pairs = new ArrayList<>(size / 2);
		Set<Integer> occupiedTeamsIndexes = new HashSet<>(size);

		for (int i = 0; i < size; i++) {
			int opponentIndex;
			do {
				opponentIndex = ThreadLocalRandom.current().nextInt(i, size);
			} while (occupiedTeamsIndexes.contains(opponentIndex));

			pairs.add(new MatchNote(teams.get(i), teams.get(opponentIndex)));
			occupiedTeamsIndexes.add(opponentIndex);
		}
		var cup = (ICup) tournament;
		cup.setPairsAfterDraw(pairs);
	}
}