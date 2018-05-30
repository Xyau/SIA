package breeders;

import individuals.Individual;
import interfaces.Breeder;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class TwoByTwoBreeder implements Breeder {
    Float chanceToBreed;
    Random random;
    Logger logger = Logger.getRootLogger();

    public TwoByTwoBreeder(Random random, Float chanceToBreed) {
        this.chanceToBreed = chanceToBreed;
        this.random = random;
    }

    @Override
    public List<Individual> breedChampions(List<Individual> champions) {
        if(champions.size() == 1){
            return champions;
        }
        champions = champions.stream().sorted().collect(Collectors.toList());
        List<Individual> offspring = new ArrayList<>();
        Integer size = champions.size();
        for (int i = 0; i < size-1; i+=2) {
            if(random.nextFloat()>chanceToBreed){
                offspring.add(champions.get(i));
                offspring.add(champions.get(i+1));
                logger.debug("Skipped breeding " + champions.get(i) + " with " + champions.get(i+1));
            } else {
                logger.debug("breeding " + champions.get(i) + " with " + champions.get(i+1));
                Collection<? extends Individual> directOffspring = breed(champions.get(i),champions.get(i+1));
                offspring.addAll(directOffspring );
                logger.debug("offspring: " + directOffspring);
            }
        }
        return offspring;
    }

    protected abstract Collection<? extends Individual> breed(Individual individualA, Individual individualB);
}
