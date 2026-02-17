package model.enums;

public enum FootballerCharacteristicsEnum {
    WEIGHT(0, "weight"),
    HEIGHT(1, "height"),

    SHORTPASS(2, "short pass"),
    LONGPASS(3, "long pass"),
    CROSSES(4, "crosses"),

    VISION(5, "vision"),
    POSITION_CHOOSING(6, "position choosing"),

    CONFIDENCE(7, "confidence"),
    DISCIPLINE(8, "discipline"),
    COMPOSURE(9, "composure"),

    FINISHING(10, "finishing"),
    LONG_SHOOTING(11, "long shooting"),

    DRIBBLING(12, "dribbling"),
    TECHNIQUE(13, "technique"),

    TACKLE(14, "tackle"),
    INTERCEPTION(15, "interception"),

    POWER(16, "power"),
    STAMINA(17, "stamina"),
    SPRINT_SPEED(18, "sprint speed"),
    HEADPLAY(19, "headplay"),

    REACTION(20, "reaction"),
    JUMPING(21, "jumping"),
    PLAYING_OUT(22, "playing out"),
    CROSS_INTERCEPTION(23, "cross interception");

    public final int arrayPos;
    private final String stringVersion;

    public static final short cnt = 24;
    FootballerCharacteristicsEnum(int pos, String stringVersion) {
        arrayPos = pos;
        this.stringVersion = stringVersion;
    }

    public static FootballerCharacteristicsEnum fromString(String name) {
        for (FootballerCharacteristicsEnum b : FootballerCharacteristicsEnum.values()) {
            if (b.stringVersion.equalsIgnoreCase(name)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No constant with text " + name + " found");
    }
}