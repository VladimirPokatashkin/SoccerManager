package model.repository;

import model.country.ICountry;
import model.enums.Nationality;

public interface ICountryRepository {
	void addCountry(ICountry country);
	ICountry country(Nationality value);
}