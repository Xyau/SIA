package breeders;

import genes.Genes;
import genes.Species;
import interfaces.Breeder;
import individuals.Individual;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleCrossBreeder implements Breeder {
    Random random;

    public SimpleCrossBreeder(Random random) {
        this.random = random;
    }

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
        Genes genes = new Genes(species.getGenotypes().stream().map(genotype -> {
                String name = genotype.getName();
                Integer valueMother = mother.getGenes().getPhenotypeByName(name).getValue();
                Integer valueFather = father.getGenes().getPhenotypeByName(name).getValue();
                return genotype.getPhenotypeValue((valueFather+valueMother)/2);
            }).collect(Collectors.toList()));
        return mother.incubate(genes);
    }
}
