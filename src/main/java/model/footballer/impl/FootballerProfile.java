package model.footballer.impl;

import model.enums.Nationality;
import model.enums.Role;
import model.enums.FootballerCharacteristicsEnum;
import model.footballer.BaseFootballerCharacteristics;
import model.footballer.IFootballerProfile;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;

public class FootballerProfile implements IFootballerProfile {
    private final String name;
    private final Nationality nationality;
    private List<Role> preferedRoles;
    private short number;
    private final LocalDate dateOfBirth;
    private int transferCost;

    private boolean injured;
    private short daysToHeal;

    private double currentPhysicalForm = 1.0;
    private double currentEmotionalState = 1.0;

    private BaseFootballerCharacteristics characteristics;

	private boolean isTransferCostValid(int transferCost) {
		return  0 <= transferCost;
	}


    public FootballerProfile(String name, Nationality nationality, List<Role> preferedRoles, LocalDate dateOfBirth,
	short number, int transferCost,	BaseFootballerCharacteristics characteristics) throws InvalidParameterException {
		if (!isTransferCostValid(transferCost)) {
			throw new InvalidParameterException("invalid number: " + number);
		}
        this.name = name;
        this.nationality = nationality;
        this.preferedRoles = preferedRoles;
		this.dateOfBirth = dateOfBirth;
        this.number = number;
		this.transferCost = transferCost;
        this.characteristics = characteristics;
    }

    @Override
    public short number() {
        return number;
    }

    @Override
    public void setNumber(short number) {
        this.number = number;
    }

    @Override
    public List<Role> preferedRoles() {
        return preferedRoles;
    }

    @Override
    public void addRole(Role role) {
        preferedRoles.add(role);
    }

    @Override
    public int transferCost() {
        return transferCost;
    }

    @Override
    public void setTransferCost(int cost) {
        transferCost = cost;
    }

    @Override
    public void increaseTransferCost(int costAdd) {
        transferCost += costAdd;
    }

    @Override
    public void decreaseTransferCost(int costLoss) {
        transferCost -= costLoss;
    }

    @Override
    public short characteristic(FootballerCharacteristicsEnum characteristic) {
        return characteristics.characteristic(characteristic);
    }

    @Override
    public BaseFootballerCharacteristics allCharacteristics() {
        return characteristics;
    }

    @Override
    public void increaseCharacteristic(FootballerCharacteristicsEnum characteristic, short add) {
        characteristics.increaseCharacteristic(characteristic, add);
    }

    @Override
    public void decreaseCharacteristic(FootballerCharacteristicsEnum characteristic, short loss) {
        characteristics.decreaseCharacteristic(characteristic, loss);
    }

    @Override
    public boolean injured() {
        return injured;
    }

    @Override
    public void setInjury(short daysToHeal) {
        injured = true;
        this.daysToHeal = daysToHeal;
    }

    @Override
    public short daysToHeal() {
        return daysToHeal;
    }

    @Override
    public void updateInjury() {
        daysToHeal -= 1;
    }

    @Override
    public double currentPhysicalForm() {
        return currentPhysicalForm;
    }

    @Override
    public void increasePhysicalForm(double add) {
        currentPhysicalForm += Math.min(1.0 - currentPhysicalForm, add);
    }

    @Override
    public void decreasePhysicalForm(double loss) {
        currentPhysicalForm -= Math.min(currentPhysicalForm, loss);
    }

    @Override
    public void setPhysicalForm(double physicalForm) {
        currentPhysicalForm = physicalForm;
    }

    @Override
    public double currentEmotionalState() {
        return currentEmotionalState;
    }

    @Override
    public void increaseEmotionalState(double add) {
        currentEmotionalState += Math.min(1.0 - currentEmotionalState, add);
    }

    @Override
    public void decreaseEmotionalState(double loss) {
        currentEmotionalState -= Math.min(currentEmotionalState, loss);
    }

    @Override
    public void setEmotionalState(double emotionalState) {
        currentEmotionalState = emotionalState;
    }

	@Override
	public LocalDate dateOfBirth() {
		return dateOfBirth;
	}

    @Override
    public String name() {
        return name;
    }

    @Override
    public Nationality nationality() {
        return nationality;
    }
}