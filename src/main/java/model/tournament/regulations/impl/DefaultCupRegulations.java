package model.tournament.regulations.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import model.tournament.regulations.IRegulations;

public class DefaultCupRegulations implements IRegulations {
	private short _amountOfTeams;

	@JsonCreator
	public DefaultCupRegulations(@JsonProperty("amount of teams")short amountOfTeams) {
		_amountOfTeams = amountOfTeams;
	}

	@Override
	public short amountOfTeams() {
		return _amountOfTeams;
	}
}