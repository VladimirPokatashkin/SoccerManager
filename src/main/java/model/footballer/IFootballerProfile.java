package model.footballer;

import model.interfaces.*;
import model.enums.FootballerCharacteristicsEnum;
import model.enums.Role;

import java.util.List;

public interface IFootballerProfile extends IHasName, IHasDateOfBirth, IHasNationality {
    short number();
    void setNumber(short number);

    List<Role> preferedRoles();
    void addRole(Role role);

    int transferCost();
    void setTransferCost(int cost);
    void increaseTransferCost(int costAdd);
    void decreaseTransferCost(int costLoss);

    short characteristic(FootballerCharacteristicsEnum characteristic);
    BaseFootballerCharacteristics allCharacteristics();
    void increaseCharacteristic(FootballerCharacteristicsEnum characteristic, short add);
    void decreaseCharacteristic(FootballerCharacteristicsEnum characteristic, short loss);

    boolean injured();
    void setInjury(short daysToHeal);
    short daysToHeal();
    void updateInjury();

    double currentPhysicalForm();
    void increasePhysicalForm(double add);
    void decreasePhysicalForm(double loss);
    void setPhysicalForm(double physicalForm);

    double currentEmotionalState();
    void increaseEmotionalState(double add);
    void decreaseEmotionalState(double loss);
    void setEmotionalState(double emotionalState);
}