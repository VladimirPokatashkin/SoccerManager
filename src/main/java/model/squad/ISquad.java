package model.squad;

import model.formation.IFormation;
import model.enums.Role;

import java.util.ArrayList;
import java.util.Map;

public interface ISquad {
	IFormation formation();
	Map<Short, Role> startingXI();
	ArrayList<Short> bench();
	void replacePlayerInStartingXI(short oldNumber, short newNumber);
	void replacePlayerOnBench(short oldNumber, short newNumber);
	void changeFormation(IFormation formation);
}