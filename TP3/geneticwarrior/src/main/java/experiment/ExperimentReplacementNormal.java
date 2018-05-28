package experiment;

import individuals.Individual;
import interfaces.Breeder;
import interfaces.Mutator;
import interfaces.Selector;

import java.util.*;

public class ExperimentReplacementNormal extends Experiment {
    Integer parentAmount;
    Random random;
    ExperimentReplacementNormal(String name, Breeder breeder, List<Individual> startingPop, Mutator mutator,
                                Selector selector, Selector replacement, Integer maxGenerations, Double targetFitness,
                                Integer maxStaleBestFitnessGenerations, Integer maxStaleIndividualsGenerations,
                                Integer parentAmount, Random random) {
        super(name, breeder, startingPop, mutator, selector, replacement, maxGenerations, targetFitness,
                maxStaleBestFitnessGenerations, maxStaleIndividualsGenerations);

        this.random = random;
        this.parentAmount = parentAmount;
    }

    @Override
    List<Individual> makeNextGeneration(List<Individual> pop, Integer genNumber) {
        Integer k = parentAmount;
        List<Individual> parents = selector.selectChampions(pop,k,genNumber);
        List<Individual> nextGen = reeplacement.selectChampions(pop,pop.size()-k,genNumber);
        logAverage(parents,"parentsAvg");
        logAverage(nextGen,"replacementAvg");

        log.debug("Selected champions: " + parents);
        List<Individual> offspring = breeder.breedChampions(parents);
        log.debug("Champions offspring: " + offspring);
        List<Individual> mutatedOffspring = mutator.mutate(offspring);
        log.debug("Mutated offspring: " + mutatedOffspring);
        nextGen.addAll(mutatedOffspring);
        log.debug("End of gen "+genNumber+": " + pop);
        return nextGen;
    }
}
