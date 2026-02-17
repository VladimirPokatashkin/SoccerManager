package model.squad;

import model.formation.IFormation;
import model.enums.Role;

import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

public class Squad implements ISquad {
	private IFormation formation;
	private Map<Short, Role> startingXI;
	private ArrayList<Short> bench;



	public Squad(IFormation formation, Map<Short, Role> startingXI, ArrayList<Short> bench) {
		this.formation = formation;
		this.startingXI = startingXI;
		this.bench = bench;
	}

	@Override
	public IFormation formation() {
		return formation;
	}

	@Override
	public Map<Short, Role> startingXI() {
		return startingXI;
	}

	@Override
	public ArrayList<Short> bench() {
		return bench;
	}

	@Override
	public void replacePlayerInStartingXI(short oldNumber, short newNumber) {
		Role role = startingXI.get(oldNumber);
		startingXI.put(newNumber, role);
		startingXI.remove(oldNumber);
	}

	@Override
	public void replacePlayerOnBench(short oldNumber, short newNumber) {
		var role = bench.stream().filter(n -> n == oldNumber).findAny();

		if (role.isEmpty()) {
			throw new NoSuchElementException("no player with " + oldNumber + " number");
		}

		bench.add(newNumber);
		bench.remove(oldNumber);
	}

	@Override
	public void changeFormation(IFormation formation) {
		this.formation = formation;
		startingXI.clear();
		bench.clear();
	}
}