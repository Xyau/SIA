package genes;

import interfaces.Phenotype;
import main.BonusType;
import main.ItemType;

import java.util.Map;
import java.util.Objects;

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
        return itemType.toString()+bonusMap.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemType, bonusMap);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPhenotype that = (ItemPhenotype) o;
        return itemType == that.itemType &&
                Objects.equals(bonusMap, that.bonusMap);
    }
}
