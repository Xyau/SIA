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

public class AnularBreeder extends TwoByTwoBreeder {

    public AnularBreeder(Random random, Float chanceToBreed){
        super(random,chanceToBreed);
    }

    protected List<Individual> breed(Individual mother, Individual father){
        List<Genotype> genotypes = mother.getSpecies().getGenotypes();
        Integer start = random.nextInt(genotypes.size());
        Integer lenght = random.nextInt(genotypes.size()/2-1)+1;
        List<Individual> individuals = new ArrayList<>();
        List<Phenotype> phenotypesA = new ArrayList<>();
        List<Phenotype> phenotypesB = new ArrayList<>();
        for (int i = 0; i < genotypes.size(); i++) {
            String name = genotypes.get(i).getName();
            Phenotype phenotypeMother = mother.getGenes().getPhenotypeByName(name);
            Phenotype phenotypeFather = father.getGenes().getPhenotypeByName(name);
            if(start+lenght>=genotypes.size()){
                phenotypesA.add(((start+lenght)%genotypes.size()< i &&  i <= start)?phenotypeFather:phenotypeMother);
                phenotypesB.add(((start+lenght)%genotypes.size()< i &&  i <= start)?phenotypeMother:phenotypeFather);
            } else {
                phenotypesA.add((i > start && i <= start+lenght)?phenotypeFather:phenotypeMother);
                phenotypesB.add((i > start && i <= start+lenght)?phenotypeMother:phenotypeFather);
            }
        }
        individuals.add(mother.incubate( new Genes(phenotypesA)));
        individuals.add(mother.incubate( new Genes(phenotypesB)));
        return individuals;
    }
}
