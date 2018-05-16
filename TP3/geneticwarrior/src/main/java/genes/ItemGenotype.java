package genes;

import interfaces.Genotype;
import interfaces.Phenotype;

import java.util.Random;

public class ItemGenotype implements Genotype {
    String name;
    Double strenght;
    Double agility;
    Double wisdom;
    Double resistance;
    Double health;

    @Override
    public Phenotype getMutation(Phenotype phenotype, Double mutationRate) {
        return null;
    }

    @Override
    public Phenotype getRandomPhenotype(Random random) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Phenotype getPhenotypeValue(Integer value) {
        return null;
    }
}
