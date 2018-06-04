package main;

public enum  BonusType {
    STRENGHT, AGILITY, WISDOM, RESISTANCE, HEALTH;

    public static BonusType fromString(String itemType){
        switch (itemType){
            case "STRENGHT":return STRENGHT;
            case "AGILITY":return AGILITY;
            case "WISDOM":return WISDOM;
            case "RESISTANCE":return RESISTANCE;
            case "HEALTH":return HEALTH;
        }
        throw new IllegalArgumentException("no such bonus name");
    }
}
