package model.footballer;

public class FootballerMatchInfo {
    private String _team;
    private short _number;
    private boolean _onField;
    private boolean _onBench;

    public FootballerMatchInfo(String team, short number, boolean onField) {
        _team = team;
        _number = number;
        _onField = onField;
        _onBench = !onField;
    }

    String team() {
        return _team;
    }

    short number() {
        return _number;
    }

    boolean onField() {
        return _onField;
    }

    boolean onBench() {
        return _onBench;
    }

    void reset(String team, short number, boolean onField) {
        _team = team;
        _number = number;
        _onField = onField;
        _onBench = !onField;
    }
}