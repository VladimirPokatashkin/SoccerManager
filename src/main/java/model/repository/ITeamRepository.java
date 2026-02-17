package model.repository;

import model.team.ITeam;

import java.util.List;

public interface ITeamRepository {
	void addTeam(ITeam team);
	ITeam teamByName(String name);
	List<ITeam> allTeams();
}