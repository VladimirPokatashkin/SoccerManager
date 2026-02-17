package model.service.schedule.impl;

import model.service.schedule.IDrawService;
import model.tournament.INationalLeague;
import model.tournament.ITournament;
import model.tournament.impl.MatchNote;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class LeagueDrawService implements IDrawService {
	private List<String> teams;
	private Set<Integer> occupiedTeamsIndexes;
	private Map<String, List<MatchNote>> schedule;
	private int teamCnt;
	private int tourCnt;

	private void holdATeamDraw(int teamIndex) {
		for (int tour = 0; tour < tourCnt; tour++) {
			if (null != schedule.get(teams.get(teamIndex)).get(tour)) {
				continue;
			}

			if (tour == tourCnt / 2) {
				occupiedTeamsIndexes.clear();
			}

			int opponentIndex;
			do {
				opponentIndex = ThreadLocalRandom.current().nextInt(teamIndex + 1, teamCnt);
			} while (null != schedule.get(teams.get(teamIndex)).get(tour) && occupiedTeamsIndexes.contains(opponentIndex));

			MatchNote match;
			if (tour < tourCnt / 2) {
				match = new MatchNote(teams.get(teamIndex), teams.get(opponentIndex));
			} else {
				match = new MatchNote(teams.get(opponentIndex), teams.get(teamIndex));
			}
			schedule.get(teams.get(teamIndex)).add(match);
			schedule.get(teams.get(opponentIndex)).add(match);
			occupiedTeamsIndexes.add(opponentIndex);
		}
		occupiedTeamsIndexes.clear();
	}


	@Override
	public void holdADraw(ITournament tournament) {
		teams = tournament.teams();
		occupiedTeamsIndexes = new HashSet<>(teamCnt);
		schedule = new TreeMap<>();
		teamCnt = teams.size();
		tourCnt = (tournament.regulations().amountOfTeams() - 1) << 1;

		teams.forEach(team ->
			schedule.put(team, new ArrayList<>(Collections.nCopies(tourCnt, null))));

		for (int i = 0; i < teamCnt; i++) {
			holdATeamDraw(i);
		}

		var league = (INationalLeague) tournament;
		league.setSchedule(schedule);
	}
}