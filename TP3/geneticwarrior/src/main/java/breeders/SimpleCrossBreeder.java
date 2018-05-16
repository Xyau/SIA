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

public class SimpleCrossBreeder implements Breeder {
    Random random;

    public SimpleCrossBreeder(Random random) {
        this.random = random;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public List<Individual> breedChampions(List<Individual> champions) {
        if(champions.size() == 1){
            return champions;
        }
        List<Individual> offspring = new ArrayList<>();
        Integer size = champions.size();
        for (int i = 0; i < size-1; i+=2) {
            offspring.add(breed(champions.get(i),champions.get(i+1)));
        }
        return offspring;
    }

    private Individual breed(Individual mother, Individual father){
        Species species = mother.getSpecies();
        Integer cross = random.nextInt(species.getGenotypes().size());
        Integer genotypeNumber=0;
        List<Phenotype> phenotypes = new ArrayList<>();
        for (Genotype genotype:species.getGenotypes()){
            String name = genotype.getName();
            Integer valueMother = mother.getGenes().getPhenotypeByName(name).getValue();
            Integer valueFather = father.getGenes().getPhenotypeByName(name).getValue();
            phenotypes.add(genotype.getPhenotypeValue(genotypeNumber>cross?valueMother:valueFather));
        }
        Genes genes = new Genes(phenotypes);
        return mother.incubate(genes);
    }
}
