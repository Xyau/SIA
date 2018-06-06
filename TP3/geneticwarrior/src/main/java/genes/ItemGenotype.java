package genes;

import interfaces.Genotype;
import interfaces.Phenotype;
import main.ItemType;

import java.util.List;
import java.util.Random;

public class ItemGenotype implements Genotype {
    private ItemType itemType;
    private List<ItemPhenotype> phenotypes;
    private Random random;

    public ItemGenotype(ItemType itemType, List<ItemPhenotype> phenotypes, Random random) {
        this.itemType = itemType;
        this.phenotypes = phenotypes;
        this.random = random;
    }

    @Override
    public Phenotype getMutation(Phenotype phenotype, Double mutationRate) {
        return getRandomPhenotype(random);
    }

    @Override
    public Phenotype getRandomPhenotype(Random random) {
        return phenotypes.get(random.nextInt(phenotypes.size()-1));
    }

    @Override
    public String getName() {
        return itemType.toString();
    }
}
