package model.footballer;

import model.interfaces.*;
import model.enums.FootballerCharacteristicsEnum;
import model.enums.Role;

public interface IPlayingFootballer extends IMovable, IHasSize {
    Role role();
    void setRole(Role role);
    double stamina();
    void increaseStamina(double add);
    void decreaseStamina(double loss);
    short ID();
    void setID(short ID);
    short charasteristic(FootballerCharacteristicsEnum characteristic);
}