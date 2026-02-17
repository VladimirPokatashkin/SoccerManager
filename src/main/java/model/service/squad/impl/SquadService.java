package model.service.squad.impl;

import model.footballer.IFootballerProfile;
import model.formation.IFormation;
import model.service.squad.ISquadFactory;
import model.service.squad.ISquadService;
import model.squad.ISquad;
import model.enums.Role;
import model.team.ITeam;

import java.util.*;

public class SquadService implements ISquadService {
    private static final short PLAYERS_ON_FIELD = 11;
    private static final short MAX_PLAYERS_ON_BENCH = 11;
    private static final short MIN_PLAYERS_ON_BENCH = 9;

    private ITeam _team;

    private short _playersOnField;
    private short _playersOnBench;
    private Map<Short, Role> _startingXI;
    private ArrayList<Short> _bench;

    private Optional<IFormation> _formation;
    private List<Role> _freeRoles;
    private List<Role> _occupiedRoles;

	private ISquadFactory _factory;

    private void permutatePlayers() {
        _startingXI.values().forEach(oldRole -> {
            var roleOptional = _freeRoles.stream().filter(r -> r == oldRole).findAny();
            if (!roleOptional.isEmpty()) {
                _freeRoles.remove(roleOptional.get());
                _occupiedRoles.add(roleOptional.get());
            } else {
                Role newRole = Collections.min(_freeRoles,
                        Comparator.comparingInt(x -> Math.abs(x.pos - oldRole.pos)));
                _freeRoles.remove(newRole);
                _occupiedRoles.add(newRole);
            }
        });
    }

	public SquadService(ISquadFactory factory) {
		_factory = factory;
	}

    public void setTeam(ITeam team) {
        _team = team;
    }

    @Override
    public void addPlayerToStartingXI(short number, Role role) throws NoSuchElementException {
        boolean suchRoleFound = !_freeRoles.stream().filter(r -> r == role).findAny().isEmpty();

        if (isFormationSet() && _playersOnField < PLAYERS_ON_FIELD && suchRoleFound) {

            Optional<IFootballerProfile> profile = _team.findPlayerByNumber(number);

            if (profile.isEmpty()) {
                throw new NoSuchElementException(
                        "no footballer with " + number + "number in team \"" + _team.name() + "\""
                );
            }

            _startingXI.put(number, role);
            _playersOnField += 1;
        }
    }

    @Override
    public void addPlayerOnBench(short number) throws NoSuchElementException {
        if (_playersOnBench < MAX_PLAYERS_ON_BENCH) {
            Optional<IFootballerProfile> profile = _team.findPlayerByNumber(number);

            if (profile.isEmpty()) {
                throw new NoSuchElementException(
                        "no footballer with " + number + "number in team \"" + _team.name() + "\""
                );
            }

            _bench.add(number);
            _playersOnBench += 1;
        }
    }

    @Override
    public void replacePlayerInStartingXI(short numberOld, short numberNew) throws NoSuchElementException {
        Optional<IFootballerProfile> profile = _team.findPlayerByNumber(numberNew);

        if (profile.isEmpty()) {
            throw new NoSuchElementException(
                    "no footballer with " + numberNew + "number in team \"" + _team.name() + "\""
            );
        }

		Role role = _startingXI.get(numberOld);
        _startingXI.remove(numberOld);
        _startingXI.put(numberNew, role);
    }

    @Override
    public void replacePlayerOnBench(short numberOld, short numberNew) throws NoSuchElementException {
        Optional<IFootballerProfile> profile = _team.findPlayerByNumber(numberNew);

        if (profile.isEmpty()) {
            throw new NoSuchElementException(
                    "no footballer with " + numberNew + "number in team \"" + _team.name() + "\""
            );
        }

        _bench.remove(numberOld);
        _bench.add(numberNew);
    }

    @Override
    public void swapPlayersInStartingXI(short numberX, short numberY) {
        Role roleX = _startingXI.get(numberX);
        Role roleY = _startingXI.get(numberY);
        _startingXI.replace(numberX, roleY);
        _startingXI.replace(numberY, roleX);
    }

    @Override
    public void setFormation(IFormation formation) {
        _formation = Optional.of(formation);
        _occupiedRoles.forEach(role -> _freeRoles.add(role));
        _occupiedRoles.clear();
        permutatePlayers();
    }

    @Override
    public Map<Short, Role> startingXI() {
        return _startingXI;
    }

    @Override
    public ArrayList<Short> bench() {
        return _bench;
    }

    @Override
    public boolean isFormationSet() {
        return !_formation.isEmpty();
    }

    @Override
    public boolean isTeamReady() {
        return _playersOnField == PLAYERS_ON_FIELD &&
                _playersOnBench < MAX_PLAYERS_ON_BENCH &&
                _playersOnBench > MIN_PLAYERS_ON_BENCH;
    }

	@Override
	public ISquad result() {
		if (isTeamReady()) {
			return _factory.create(_formation.get(), _startingXI, _bench);
		}
		return null;
	}

	@Override
	public void addSquadToChange(ISquad squad) {
		_formation = Optional.of(squad.formation());
		_startingXI = squad.startingXI();
		_bench = squad.bench();
		_freeRoles.clear();
		Arrays.stream(_formation.get().roles()).forEach(role -> _occupiedRoles.add(role));
	}
}