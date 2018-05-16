package interfaces;

import interfaces.Phenotype;
import main.Fenotype;

import java.util.NavigableSet;
import java.util.Random;

public interface Genotype {
    /**
     * Given a fenotype of the same type of this Genotype it returns
     * another Fenotype of the same type but mutated acording to mutation rate
     * mutation rate of < 0*/
    Phenotype getMutation(Phenotype phenotype, Double mutationRate);

    Phenotype getRandomPhenotype(Random random);

    String getName();

    Phenotype getPhenotypeValue(Integer value);
}
