package model.tournament;

import model.interfaces.IHasName;
import model.tournament.regulations.IRegulations;
import model.tournament.impl.MatchNote;

import java.util.List;

public interface ITournament extends IHasName {
	void resetResults();
	void replaceTeam(String oldTeam, String newTeam);
	List<String> teams();
	IRegulations regulations();
	List<MatchNote> nextStageMatches();
	List<MatchNote> allTeamMatches(String team);
	List<MatchNote> currentStageMatches();
}