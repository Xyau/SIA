package experiment;

import individuals.Individual;
import interfaces.Breeder;
import interfaces.Mutator;
import interfaces.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExperimentReplacementComplex extends Experiment {
    Double untouched;
    Random random;
    ExperimentReplacementComplex(Breeder breeder, List<Individual> startingPop, Mutator mutator, Selector selector,
                                 Integer maxGenerations, Integer workingPop, String name, Double untouched, Random random) {
        super(breeder, startingPop, mutator, selector, maxGenerations, workingPop, name);
        this.random = random;
        this.untouched = untouched;
    }

    @Override
    List<Individual> makeNextGeneration(List<Individual> pop, Integer genNumber) {
        Integer k = random.nextInt(new Double(Math.ceil(pop.size()*untouched)).intValue());
        List<Individual> parents = selector.selectChampions(pop,k,genNumber);
        List<Individual> nextGen = new ArrayList<>();
        nextGen.addAll(pop);
        logAverage(parents,"parentsAvg");

        log.finest("Selected champions: " + parents);
        List<Individual> offspring = breeder.breedChampions(parents);
        log.finest("Champions offspring: " + offspring);
        List<Individual> mutatedOffspring = mutator.mutate(offspring);
        log.finest("Mutated offspring: " + mutatedOffspring);
        nextGen.addAll(mutatedOffspring);
        logAverage(mutatedOffspring,"offspringAvg");
        nextGen = selector.selectChampions(nextGen,workingPop,genNumber);
        log.finest("End of gen "+genNumber+": " + pop);
        return nextGen;
    }
}
