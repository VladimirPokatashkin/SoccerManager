package model.service.squad.impl;

import model.service.squad.ISquadFactory;
import model.squad.Squad;
import model.formation.IFormation;
import model.squad.ISquad;
import model.enums.Role;

import java.util.ArrayList;
import java.util.Map;

public class SquadFactory implements ISquadFactory {

	@Override
	public ISquad create(IFormation formation, Map<Short, Role> startingXI, ArrayList<Short> bench) {
		return new Squad(formation, startingXI, bench);
	}
}