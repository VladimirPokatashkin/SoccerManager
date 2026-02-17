package model.repository;

public interface ILocator {
	ICountryRepository countryRepository();
	ITeamRepository teamRepository();
	ITournamentRepository tournamentRepository();
}