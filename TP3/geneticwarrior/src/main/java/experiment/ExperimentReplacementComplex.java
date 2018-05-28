package experiment;

import individuals.Individual;
import interfaces.Breeder;
import interfaces.Mutator;
import interfaces.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExperimentReplacementComplex extends Experiment {
    Integer parentAmount;
    Random random;
    ExperimentReplacementComplex(String name, Breeder breeder, List<Individual> startingPop, Mutator mutator,
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
        List<Individual> survivors = reeplacement.selectChampions(pop,pop.size()-k,genNumber);
        logAverage(parents,"parentsAvg");

        log.debug("Selected champions: " + parents);
        List<Individual> offspring = breeder.breedChampions(parents);
        log.debug("Champions offspring: " + offspring);
        List<Individual> mutatedOffspring = mutator.mutate(offspring);
        log.debug("Mutated offspring: " + mutatedOffspring);
        List<Individual> extras = new ArrayList<>();
        extras.addAll(mutatedOffspring);
        extras.addAll(pop);
        extras = reeplacement.selectChampions(extras,k,genNumber);
        extras.addAll(survivors);
        log.debug("End of gen "+genNumber+": " + pop);
        return extras;
    }
}
