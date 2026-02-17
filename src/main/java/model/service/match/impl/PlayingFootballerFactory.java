package model.service.match.impl;

import model.footballer.impl.PlayingFootballer;
import model.footballer.IFootballerProfile;
import model.footballer.IPlayingFootballer;
import model.service.match.IPlayingFootballerFactory;
import model.enums.Role;

public class PlayingFootballerFactory implements IPlayingFootballerFactory {
    @Override
    public IPlayingFootballer produce(IFootballerProfile player, Role role) {
        return new PlayingFootballer(player.allCharacteristics(), role);
    }

    @Override
    public IPlayingFootballer produce(IFootballerProfile player) {
        Role role = player.preferedRoles().getFirst();
        return produce(player, role);
    }
}