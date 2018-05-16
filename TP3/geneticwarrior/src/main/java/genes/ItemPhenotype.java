package genes;

import interfaces.Phenotype;
import main.BonusType;
import main.ItemType;

import java.util.Map;

public class ItemPhenotype implements Phenotype {
    private String  name;
    private ItemType itemType;
    Map<BonusType,Double> bonusMap;

    public ItemPhenotype(String name, ItemType itemType, Map<BonusType, Double> bonusMap) {
        this.name = name;
        this.itemType = itemType;
        this.bonusMap = bonusMap;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getValue(String key) {
        return bonusMap.get(key);
    }
}
