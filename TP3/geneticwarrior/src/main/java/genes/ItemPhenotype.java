package genes;

import interfaces.Phenotype;
import main.BonusType;
import main.ItemType;

import java.util.Map;

public class ItemPhenotype implements Phenotype {
    private ItemType itemType;
    Map<BonusType,Float> bonusMap;

    public ItemPhenotype(String name, ItemType itemType, Map<BonusType, Float> bonusMap) {
        this.itemType = itemType;
        this.bonusMap = bonusMap;
    }

    @Override
    public String getName() {
        return itemType.toString();
    }

    @Override
    public Float getValue(String key) {
        return bonusMap.get(BonusType.fromString(key));
    }

    @Override
    public String toString() {
        return itemType.toString();
    }
}
