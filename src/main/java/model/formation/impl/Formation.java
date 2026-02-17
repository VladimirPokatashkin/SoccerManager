package model.formation.impl;

import model.enums.Role;
import model.formation.IFormation;

public class Formation implements IFormation {
    private String textDescription;
    private Role[] roles;

    @Override
    public String textDescription() {
        return textDescription;
    }

    @Override
    public Role[] roles() {
        return roles;
    }

    @Override
    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    @Override
    public void setRoles(Role[] roles) {
        this.roles = roles;
    }
}