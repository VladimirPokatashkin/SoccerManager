package model.field;

import model.ball.IBall;
import model.interfaces.IHasSize;
import model.footballer.IPlayingFootballer;

public interface IField extends IHasSize {
    short addPlayer(IPlayingFootballer player);
    void substitutePlayer(short ID, IPlayingFootballer playerFromBench);
    IBall Ball();
}