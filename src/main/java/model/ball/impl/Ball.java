package model.ball.impl;

import model.ball.IBall;
import shared.Vector3;

public class Ball implements IBall {
    private Vector3 speed = new Vector3(0, 0, 0);
    private Vector3 acceleration = new Vector3(0, 0, 0);
    private  Vector3 position;

    public Ball(Vector3 position) {
        this.position = position;
    }

    @Override
    public void move(Vector3 shift) {
        position.addLocal(shift);
    }

    @Override
    public Vector3 speed() {
        return speed;
    }

    @Override
    public void setSpeed(Vector3 speed) {
        this.speed = speed;
    }

    @Override
    public void increaseSpeed(Vector3 speedAdd) {
        speed.addLocal(speedAdd);
    }

    @Override
    public void decreaseSpeed(Vector3 speedLoss) {
        speedLoss.mulLocal(-1.0);
        speed.addLocal(speedLoss);
    }

    @Override
    public Vector3 acceleration() {
        return acceleration;
    }

    @Override
    public void setAcceleration(Vector3 acceleration) {
        this.acceleration = acceleration;
    }

    @Override
    public Vector3 position() {
        return position;
    }
}