package breeders;

import genes.Genes;
import individuals.Individual;
import interfaces.Genotype;
import interfaces.Phenotype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UniformBreeder extends TwoByTwoBreeder {
    Random random;

    public UniformBreeder(Random random) {
        this.random = random;
    }

    protected List<Individual> breed(Individual mother, Individual father){
        List<Genotype> genotypes = mother.getSpecies().getGenotypes();
        List<Individual> individuals = new ArrayList<>();
        List<Phenotype> phenotypesA = new ArrayList<>();
        List<Phenotype> phenotypesB = new ArrayList<>();
        for (Genotype genotype:genotypes) {
            String name = genotype.getName();
            Phenotype phenotypeMother = mother.getGenes().getPhenotypeByName(name);
            Phenotype phenotypeFather = father.getGenes().getPhenotypeByName(name);
            Boolean usesMotherDna = random.nextBoolean();
            phenotypesB.add((usesMotherDna)?phenotypeMother:phenotypeFather);
            phenotypesA.add((usesMotherDna)?phenotypeFather:phenotypeMother);
        }
        individuals.add(mother.incubate( new Genes(phenotypesA)));
        individuals.add(mother.incubate( new Genes(phenotypesB)));
        return individuals;
    }
}
