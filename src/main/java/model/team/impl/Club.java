package model.team.impl;

import model.footballer.IFootballerProfile;
import model.records.Coach;
import model.records.Stadium;
import model.squad.ISquad;
import model.team.ITeam;

import java.security.InvalidParameterException;
import java.util.*;

public class Club implements ITeam {
	private static final short AMOUNT_OF_NUMBERS = 99;
	private String name;
	private Stadium homeStadium;
    private Coach headCoach;
	private List<ISquad> defaultSquads;
    private List<IFootballerProfile> players;
    private int transferBudget;
	private ArrayList<Boolean> isNumbervalid;

	private boolean isNumberValid(short number) {
		if (number <= 0 || number >= 100) {
			return false;
		}
		return isNumbervalid.get(number - 1);
	}

	private List<Short> invalidNumbers(List<IFootballerProfile> players) {
		List<Boolean> found = new ArrayList<>(Collections.nCopies(AMOUNT_OF_NUMBERS + 1, false));
		List<Short> res = new ArrayList<>();
		for (var player : players) {
			if (found.get(player.number()) || 0 >= player.number() || 100 <= player.number()) {
				res.add(player.number());
			}
			found.set(player.number() - 1, true);
		}
		return res;
	}


    public Club(String name, Stadium homeStadium, Coach headCoach, List<IFootballerProfile> players, int transferBudget)
	throws InvalidParameterException {
		List<Short> invalidNumbers = invalidNumbers(players);
		if (!invalidNumbers.isEmpty()) {
			throw new InvalidParameterException("invalid numbers in team '" + name + "': " + invalidNumbers);
		}
		isNumbervalid = new ArrayList<>(Collections.nCopies(AMOUNT_OF_NUMBERS, true));
        this.name = name;
		this.homeStadium = homeStadium;
		this.headCoach = headCoach;
		this.players = players;
        this.transferBudget = transferBudget;
		this.players.forEach(p -> {
			isNumbervalid.set(p.number(), false);
		});
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void addPlayer(IFootballerProfile player) throws InvalidParameterException {
		if (!isNumberValid(player.number())) {
			throw new InvalidParameterException("invalid number: " + player.number());
		}
        players.add(player);
    }

    @Override
    public void setHeadCoach(Coach coach) {
        headCoach = coach;
    }

	@Override
	public List<ISquad> defaultSqauds() {
		return defaultSquads;
	}

	@Override
	public void addDefaultSquad(ISquad squad) {
		defaultSquads.add(squad);
	}

	@Override
    public List<IFootballerProfile> allPlayers() {
        return players;
    }

    @Override
    public Optional<IFootballerProfile> findPlayerByNumber(short number) {
        return players.stream().filter(p -> p.number() == number).findAny();
    }

	@Override
	public Stadium homeStadion() {
		return homeStadium;
	}

	public void increaseTransferBudget(int add) {
		transferBudget += add;
	}

	public void decreaseTransferBudget(int loss) {
		transferBudget -= Math.min(loss, transferBudget);
	}

	public int transferBudget() {
		return transferBudget;
	}
}