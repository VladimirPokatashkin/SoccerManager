package model.country;

import model.interfaces.IHasNationality;
import model.tournament.INationalLeague;

public interface ICountry extends IHasNationality {
	INationalLeague premierLeague();
	INationalLeague divisionByNumber(short number);
	void addLeague(INationalLeague league);
}