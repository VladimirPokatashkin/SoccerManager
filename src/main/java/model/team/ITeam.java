package model.team;

import model.interfaces.IHasName;
import model.records.Coach;
import model.footballer.IFootballerProfile;
import model.squad.ISquad;
import model.records.Stadium;

import java.util.List;
import java.util.Optional;

public interface ITeam extends IHasName {
    void addPlayer(IFootballerProfile player);
    void setHeadCoach(Coach coach);
	List<ISquad> defaultSqauds();
	void addDefaultSquad(ISquad squad);
    List<IFootballerProfile> allPlayers();
    Optional<IFootballerProfile> findPlayerByNumber(short number);
	Stadium homeStadion();
}