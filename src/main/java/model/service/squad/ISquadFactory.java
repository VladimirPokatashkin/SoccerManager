package model.service.squad;

import model.formation.IFormation;
import model.squad.ISquad;
import model.enums.Role;

import java.util.ArrayList;
import java.util.Map;

public interface ISquadFactory {
	ISquad create(IFormation formation, Map<Short, Role> startingXI, ArrayList<Short> bench);
}