package model.service.loading.impl;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.country.ICountry;
import model.enums.FootballerCharacteristicsEnum;
import model.enums.Nationality;
import model.enums.Role;
import model.footballer.BaseFootballerCharacteristics;
import model.records.Stadium;
import model.repository.ICountryRepository;
import model.repository.ITeamRepository;
import model.repository.ITournamentRepository;
import model.team.impl.Club;
import model.records.Coach;
import model.footballer.impl.FootballerProfile;
import model.footballer.IFootballerProfile;
import model.tournament.INationalLeague;
import model.team.ITeam;
import model.country.impl.Country;
import model.tournament.impl.DefaultCup;
import model.tournament.ITournament;
import model.tournament.impl.NationalLeague;
import model.tournament.impl.SwissSystemCup;
import model.service.loading.IEntityLoader;
import model.tournament.regulations.impl.DefaultCupRegulations;
import model.tournament.regulations.impl.LeagueRegulations;
import model.tournament.regulations.impl.SwissSystemCupRegulations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class JsonEntityLoader implements IEntityLoader {
	private ObjectMapper mapper;
	private Path srcPath;
	private ICountryRepository countryRepo;
	private ITeamRepository teamRepo;
	private ITournamentRepository tournamentRepo;


	public static abstract class FootballerCharacteristicsMixin {
		@JsonCreator
		FootballerCharacteristicsMixin(
			Map<String, Short> characteristics
		) {}
	}

	@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type"
	)
	@JsonSubTypes({
		@JsonSubTypes.Type(value = FootballerProfile.class, name = "default")
	})
	public static abstract class IFootballerProfileMixin {}

	public static abstract class FootballerProfileMixin {
		@JsonCreator
		FootballerProfileMixin(
			@JsonProperty("name")String name,
			@JsonProperty("nationality") Nationality nationality,
			@JsonProperty("prefered roles")List<Role> preferedRoles,
			@JsonProperty("date of birth")LocalDate dateOfBirth,
			@JsonProperty("number")short number,
			@JsonProperty("transfer cost")int transferCost,
			@JsonProperty("characteristics") BaseFootballerCharacteristics characteristics
		) {}
	}

	public static abstract class FootballerCharacteristicsEnumMixin {
		@JsonValue
		abstract String getStringVersion();

		@JsonCreator
		static FootballerCharacteristicsEnum fromString(String value) {
			return null;
		}
	}

	public static abstract class CoachMixin {
		@JsonCreator
		CoachMixin(
			@JsonProperty("name")String name,
			@JsonProperty("nationality")Nationality nationality,
			@JsonProperty("date of birth")LocalDate dateOfBirth
		) {}
	}

	public static abstract class StadiumMixin {
		@JsonCreator
		StadiumMixin(
			@JsonProperty("name")String name,
			@JsonProperty("capacity")int capacity
		) {}
	}

	public static abstract class ClubMixin {
		@JsonCreator
		ClubMixin(
			@JsonProperty("name")String name,
			@JsonProperty("home stadium") Stadium homeStadium,
			@JsonProperty("head coach")Coach headCoach,
			@JsonProperty("players")List<IFootballerProfile> players,
			@JsonProperty("transfer budget")int transferBudget
		) {}
	}

	@JsonTypeInfo(
			use = JsonTypeInfo.Id.CLASS,
			include = JsonTypeInfo.As.PROPERTY,
			property = "type"
	)@JsonSubTypes({
			@JsonSubTypes.Type(value = DefaultCup.class, name = "default cup"),
			@JsonSubTypes.Type(value = SwissSystemCup.class, name = "swiss system cup"),
	})
	public static abstract class TournamentMixin {}

	public static abstract class DefaultCupRegulationsMixin {
		@JsonCreator
		DefaultCupRegulationsMixin(
			@JsonProperty("amount of teams")short amountOfTeams
		) {}
	}

	public static abstract class DefaultCupMixin {
		@JsonCreator
		DefaultCupMixin(
			@JsonProperty("name")String name,
			@JsonProperty("regulations") DefaultCupRegulations regulations,
			@JsonProperty("participants")List<String> participants
		) {}
	}

	public static abstract class LeagueRegulationsMixin {
		@JsonCreator
		LeagueRegulationsMixin(
			@JsonProperty("amount of teams") short amountOfTeams,
			@JsonProperty("teams to promote") short teamsToPromote,
			@JsonProperty("teams to eliminate") short teamsToEliminate
		) {}
	}


	public static abstract class SwissSystemCupRegulationsMixin {
		@JsonCreator
		SwissSystemCupRegulationsMixin(
			@JsonProperty("league phase members")short leaguePhaseMembers,
			@JsonProperty("league phase matches")short leaguePhaseMatches,
			@JsonProperty("pots")short pots,
			@JsonProperty("direct play-off clubs")short directPlayOffClubs,
			@JsonProperty("indirect play-off clubs")short indirectPlayOffClubs
		) {}
	}

	public static abstract class SwissSystemCupMixin {
		@JsonCreator
		SwissSystemCupMixin(
			@JsonProperty("name")String name,
			@JsonProperty("regulations") SwissSystemCupRegulations regulations,
			@JsonProperty("participants")List<String> participants
		) {}
	}

	private String loadClub(Path teamConfigPath) throws IOException, InvalidParameterException {
		ITeam club = mapper.readValue(
			srcPath.resolve(teamConfigPath).toFile(),
			new TypeReference<Club>() {}
		);
		teamRepo.addTeam(club);
		return club.name();
	}

	private INationalLeague loadLeague(Path leagueConfigPath) throws IOException {
		LeagueRegulations regulations = mapper.readValue(
			srcPath.resolve(leagueConfigPath + "/regulations.json").toFile(),
			new TypeReference<>() {});
		INationalLeague league = new NationalLeague(leagueConfigPath.getFileName().toString(), regulations);

		try(Stream<Path> clubs = Files.list(srcPath.resolve(leagueConfigPath + "/clubs"))) {
			clubs.forEach(club -> {
				try {
					String team = loadClub(club);
					league.addTeam(team);
				} catch (IOException exception) {
					System.err.println("invalid config file: " + exception.getMessage());
					throw new RuntimeException(exception);
				}
			});
			return league;
		} catch (Exception exception) {
			System.err.println("Could not read config file: " + exception.getMessage());
			throw new IOException(exception);
		}
	}

	private ICountry loadCountry(Path countryConfigPath) throws IOException {
		try (Stream<Path> leagues = Files.list(srcPath.resolve(countryConfigPath))) {
			ICountry country = new Country(countryConfigPath.getFileName().toString());

			leagues.forEach(path -> {
				try {
					INationalLeague league = loadLeague(path);
					tournamentRepo.addTournament(league);
					league.teams().forEach(t -> tournamentRepo.setTournamentToTeam(t, league.name()));
					country.addLeague(league);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			});

			return country;
		} catch (Exception exception) {
			System.err.println("invalid config file: " + exception.getMessage());
			throw new IOException(exception);
		}
	}

	public JsonEntityLoader(Path srcPath, ICountryRepository countryRepo,
							ITeamRepository teamRepo, ITournamentRepository tournamentRepo) {
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		this.srcPath = srcPath;
		this.countryRepo = countryRepo;
		this.teamRepo = teamRepo;
		this.tournamentRepo = tournamentRepo;

		mapper.addMixIn(FootballerCharacteristicsEnum.class, FootballerCharacteristicsEnumMixin.class);
		mapper.addMixIn(BaseFootballerCharacteristics.class, FootballerCharacteristicsMixin.class);
		mapper.addMixIn(IFootballerProfile.class, IFootballerProfileMixin.class);
		mapper.addMixIn(FootballerProfile.class, FootballerProfileMixin.class);
		mapper.addMixIn(Coach.class, CoachMixin.class);
		mapper.addMixIn(Stadium.class, StadiumMixin.class);
		mapper.addMixIn(Club.class, ClubMixin.class);
		mapper.addMixIn(ITournament.class, TournamentMixin.class);
		mapper.addMixIn(DefaultCupRegulations.class, DefaultCupRegulationsMixin.class);
		mapper.addMixIn(DefaultCup.class, DefaultCupMixin.class);
		mapper.addMixIn(LeagueRegulations.class, LeagueRegulationsMixin.class);
		mapper.addMixIn(SwissSystemCupRegulations.class, SwissSystemCupRegulationsMixin.class);
		mapper.addMixIn(SwissSystemCup.class, SwissSystemCupMixin.class);
	}

	@Override
	public void loadClubsAndLeagues(Path configPath) throws IOException {
		try(Stream<Path> countries = Files.list(srcPath.resolve(configPath))) {
			countries.forEach(path -> {
				try {
					ICountry country = loadCountry(path);
					countryRepo.addCountry(country);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			});

		} catch (Exception exception) {
			System.err.println("Could not read config file: " + exception.getMessage());
			throw new IOException(exception);
		}
	}

	@Override
	public void loadTournaments(Path configPath) throws IOException {
		try(Stream<Path> tournaments = Files.list(srcPath.resolve(configPath))) {
			tournaments.forEach(path -> {
				try {
					ITournament tournament = mapper.readValue(
						path.toFile(),
						new TypeReference<>() {}
					);
					tournamentRepo.addTournament(tournament);
					tournament.teams().forEach(t -> tournamentRepo.setTournamentToTeam(t, tournament.name()));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			});
		} catch (Exception exception) {
			System.err.println("Could not read config file: " + exception.getMessage());
			throw new IOException(exception);
		}
	}
}