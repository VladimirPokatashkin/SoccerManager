package model.service.match;

import model.footballer.IFootballerProfile;
import model.footballer.IPlayingFootballer;
import model.enums.Role;

public interface IPlayingFootballerFactory {
    IPlayingFootballer produce(IFootballerProfile player, Role role);
    IPlayingFootballer produce(IFootballerProfile player);
}