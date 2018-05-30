package genes;

import interfaces.Genotype;
import interfaces.Phenotype;
import main.ItemType;
import utils.TSVReader;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public class CachedItemGenotype implements Genotype {
    private ItemType itemType;
    private ConcurrentLinkedQueue<ItemPhenotype> phenotypes;

    public CachedItemGenotype(ItemType itemType, ConcurrentLinkedQueue<ItemPhenotype> phenotypes) {
        this.itemType = itemType;
        this.phenotypes = phenotypes;
    }

    @Override
    public Phenotype getMutation(Phenotype phenotype, Double mutationRate) {
        return getRandomPhenotype(new Random());
    }

    @Override
    public Phenotype getRandomPhenotype(Random random) {
        if(phenotypes.isEmpty()){
            TSVReader.parseFileAndRefillQueue(itemType, phenotypes, random);
        }
        return phenotypes.poll();
    }

    @Override
    public String getName() {
        return itemType.toString();
    }
}
