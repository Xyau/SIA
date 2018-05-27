package breeders;

import genes.Genes;
import genes.Species;
import individuals.Individual;
import interfaces.Breeder;
import interfaces.Genotype;
import interfaces.Phenotype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class SimpleCrossBreeder extends TwoByTwoBreeder{
    public SimpleCrossBreeder(Random random, Float chanceToBreed) {
        super(random,chanceToBreed);
    }

    protected List<Individual> breed(Individual mother, Individual father){
        Species species = mother.getSpecies();
        Integer cross = random.nextInt(species.getGenotypes().size());
        Integer genotypeNumber=0;
        List<Individual> individuals = new ArrayList<>();
        List<Phenotype> phenotypesA = new ArrayList<>();
        List<Phenotype> phenotypesB = new ArrayList<>();
        for (Genotype genotype:species.getGenotypes()){
            String name = genotype.getName();
            Phenotype phenotypeMother = mother.getGenes().getPhenotypeByName(name);
            Phenotype phenotypeFather = father.getGenes().getPhenotypeByName(name);
            phenotypesA.add(genotypeNumber<cross?phenotypeMother:phenotypeFather);
            phenotypesB.add(genotypeNumber<cross?phenotypeFather:phenotypeMother);
            genotypeNumber++;
        }
        individuals.add(mother.incubate( new Genes(phenotypesA)));
        individuals.add(mother.incubate( new Genes(phenotypesB)));
        return individuals;
    }
}
