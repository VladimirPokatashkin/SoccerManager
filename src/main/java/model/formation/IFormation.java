package model.formation;

import model.enums.Role;

public interface IFormation {
    String textDescription();
    Role[] roles();
    void setTextDescription(String textDescription);
    void setRoles(Role[] roles);
}