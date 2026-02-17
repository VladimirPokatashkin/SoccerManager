package model.enums;

public enum Role {
    GK("GK", (short) 1),
    LB("LB", (short) 2),
    LCB("LCB", (short) 3),
    RCB("RCB", (short) 3),
    CB("CB", (short) 3),
    RB("RB", (short) 4),
    CDM("CDM", (short) 10),
    CM("CM", (short) 10),
    LM("LM", (short) 11),
    RM("RM", (short) 12),
    CAM("CAM", (short) 13),
    CF("CF", (short) 13),
    ST("ST", (short) 14),
    LW("LW", (short) 15),
    RW("RW", (short) 16);

    public final String description;
    public final short pos;

    Role(String description, short pos) {
        this.description = description;
        this.pos = pos;
    }
}