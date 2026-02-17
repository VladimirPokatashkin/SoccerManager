package model.footballer.impl;

import model.enums.FootballerCharacteristicsEnum;
import model.enums.Role;
import model.footballer.BaseFootballerCharacteristics;
import model.footballer.IPlayingFootballer;
import shared.Vector3;

public class PlayingFootballer implements IPlayingFootballer {
    private BaseFootballerCharacteristics _characteristics;
    private Vector3 _speed = new Vector3(0, 0, 0);
    private Vector3 _acceleration = new Vector3(0, 0, 0);

    private double _stamina = 1.0;
    private short _ID;

    private Role _role;
    private Vector3 _size;
    private Vector3 _position;

    public PlayingFootballer(BaseFootballerCharacteristics characteristics, Role role) {
        _characteristics = characteristics;
        _role = role;

        // пока хз что сделать с размером футболиста
        _size = new Vector3(0.25, 0.25, characteristics.characteristic(FootballerCharacteristicsEnum.HEIGHT));
    }

    @Override
    public Role role() {
        return _role;
    }

    @Override
    public void setRole(Role role) {
        _role = role;
    }

    @Override
    public double stamina() {
        return _stamina;
    }

    @Override
    public void increaseStamina(double add) {
        _stamina += add;
    }

    @Override
    public void decreaseStamina(double loss) {
        _stamina -= loss;
    }

    @Override
    public short ID() {
        return _ID;
    }

    @Override
    public void setID(short ID) {
        _ID = ID;
    }

    @Override
    public short charasteristic(FootballerCharacteristicsEnum characteristic) {
        return _characteristics.characteristic(characteristic);
    }

    @Override
    public Vector3 size() {
        return _size;
    }

    @Override
    public double width() {
        return _size.x;
    }

    @Override
    public double length() {
        return _size.y;
    }

    @Override
    public double height() {
        return _size.z;
    }

    @Override
    public void move(Vector3 shift) {
        _position.addLocal(shift);
    }

    @Override
    public Vector3 speed() {
        return _speed;
    }

    @Override
    public void setSpeed(Vector3 speed) {
        _speed = speed;
    }

    @Override
    public void increaseSpeed(Vector3 speedAdd) {
        _speed.addLocal(speedAdd);
    }

    @Override
    public void decreaseSpeed(Vector3 speedLoss) {
        speedLoss.mulLocal(-1.0);
        _speed.addLocal(speedLoss);
    }

    @Override
    public Vector3 acceleration() {
        return _acceleration;
    }

    @Override
    public void setAcceleration(Vector3 acceleration) {
        _acceleration = acceleration;
    }

    @Override
    public Vector3 position() {
        return _position;
    }
}