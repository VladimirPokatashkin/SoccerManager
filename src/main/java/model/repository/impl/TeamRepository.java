package model.repository.impl;

import model.repository.ITeamRepository;
import model.team.ITeam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamRepository implements ITeamRepository {
	private Map<String, ITeam> teams;


	public TeamRepository() {
		teams = new HashMap<>();
	}

	@Override
	public void addTeam(ITeam team) {
		teams.put(team.name(), team);
	}

	@Override
	public ITeam teamByName(String name) {
		return teams.get(name);
	}

	@Override
	public List<ITeam> allTeams() {
		return List.of((ITeam) teams.values());
	}
}