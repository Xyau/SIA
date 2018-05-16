package main;

public enum ItemType {
    HELMET, WEAPON, BOOTS, GLOVES, ARMOR;

    public ItemType fromString(String itemType){
        switch (itemType){
            case "helmet":return HELMET;
            case "weapon":return WEAPON;
            case "boots":return BOOTS;
            case "armor":return ARMOR;
            case "gloves":return GLOVES;
        }
        throw new IllegalArgumentException("no such item name");
    }
}
