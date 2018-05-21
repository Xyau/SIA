package breeders;

import individuals.Individual;
import interfaces.Breeder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class TwoByTwoBreeder implements Breeder {
    @Override
    public List<Individual> breedChampions(List<Individual> champions) {
        if(champions.size() == 1){
            return champions;
        }
        champions = champions.stream().distinct().sorted().collect(Collectors.toList());
        List<Individual> offspring = new ArrayList<>();
        Integer size = champions.size();
        for (int i = 0; i < size-1; i+=2) {
            offspring.addAll(breed(champions.get(i),champions.get(i+1)));
        }
        return offspring;
    }

    protected abstract Collection<? extends Individual> breed(Individual individualA, Individual individualB);
}
