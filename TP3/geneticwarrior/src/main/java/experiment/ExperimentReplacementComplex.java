package experiment;

import individuals.Individual;
import interfaces.Breeder;
import interfaces.Mutator;
import interfaces.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExperimentReplacementComplex extends Experiment {
    Random random;
    Integer parentAmount;
    ExperimentReplacementComplex(String name,Breeder breeder, List<Individual> startingPop, Mutator mutator, Selector selector,
                                 Selector replacement, Integer maxGenerations, Integer parentAmount, Random random) {
        super(name,breeder,startingPop,mutator,selector,replacement,maxGenerations);
        this.parentAmount = parentAmount;
        this.random = random;
    }

    @Override
    List<Individual> makeNextGeneration(List<Individual> pop, Integer genNumber) {
        Integer k = parentAmount;
        List<Individual> parents = selector.selectChampions(pop,k,genNumber);
        List<Individual> survivors = reeplacement.selectChampions(pop,pop.size()-k,genNumber);
        logAverage(parents,"parentsAvg");

        log.finest("Selected champions: " + parents);
        List<Individual> offspring = breeder.breedChampions(parents);
        log.finest("Champions offspring: " + offspring);
        List<Individual> mutatedOffspring = mutator.mutate(offspring);
        log.finest("Mutated offspring: " + mutatedOffspring);
        List<Individual> extras = new ArrayList<>();
        extras.addAll(mutatedOffspring);
        extras.addAll(pop);
        extras = reeplacement.selectChampions(extras,k,genNumber);
        extras.addAll(survivors);
        log.finest("End of gen "+genNumber+": " + pop);
        return extras;
    }
}
