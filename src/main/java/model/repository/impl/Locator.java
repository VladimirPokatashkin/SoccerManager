package model.repository.impl;

import model.repository.ICountryRepository;
import model.repository.ILocator;
import model.repository.ITeamRepository;
import model.repository.ITournamentRepository;

public class Locator implements ILocator {
	private ICountryRepository countryRepository;
	private ITournamentRepository tournamentRepository;
	private ITeamRepository teamRepository;

	public Locator() {
		countryRepository = new CountryRepository();
		tournamentRepository = new TournamentRepository();
		teamRepository = new TeamRepository();
	}

	@Override
	public ICountryRepository countryRepository() {
		return countryRepository;
	}

	@Override
	public ITeamRepository teamRepository() {
		return teamRepository;
	}

	@Override
	public ITournamentRepository tournamentRepository() {
		return tournamentRepository;
	}
}