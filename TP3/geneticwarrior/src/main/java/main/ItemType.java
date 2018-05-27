package main;

public enum ItemType {
    HELMET, WEAPON, BOOTS, GLOVES, ARMOR;

    public ItemType fromString(String itemType){
        switch (itemType){
            case "HELMET":return HELMET;
            case "WEAPON":return WEAPON;
            case "BOOTS":return BOOTS;
            case "ARMOR":return ARMOR;
            case "GLOVES":return GLOVES;
        }
        throw new IllegalArgumentException("no such item name");
    }
}
