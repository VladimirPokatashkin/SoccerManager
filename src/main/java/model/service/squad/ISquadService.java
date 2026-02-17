package model.service.squad;

import model.formation.IFormation;
import model.squad.ISquad;
import model.enums.Role;

import java.util.ArrayList;
import java.util.Map;


public interface ISquadService {
    void addPlayerToStartingXI(short number, Role role);
    void addPlayerOnBench(short number);
    void replacePlayerInStartingXI(short numberOld, short numberNew);
    void replacePlayerOnBench(short numberOld, short numberNew);
    void swapPlayersInStartingXI(short numberX, short numberY);
    void setFormation(IFormation formation);
    Map<Short, Role> startingXI();
    ArrayList<Short> bench();
    boolean isFormationSet();
    boolean isTeamReady();
	ISquad result();
	void addSquadToChange(ISquad squad);
}