package experiment;

import individuals.Individual;
import interfaces.Breeder;
import interfaces.Mutator;
import interfaces.Selector;

import java.util.*;

public class ExperimentReplacementNormal extends Experiment {
    Double untouched;
    Random random;
    ExperimentReplacementNormal(Breeder breeder, List<Individual> startingPop, Mutator mutator, Selector selector,
                                Integer maxGenerations, Integer workingPop, String name, Double untouched, Random random) {
        super(breeder, startingPop, mutator, selector, maxGenerations, workingPop, name);
        this.random = random;
        this.untouched = untouched;
    }

    @Override
    List<Individual> makeNextGeneration(List<Individual> pop, Integer genNumber) {
        Integer k = random.nextInt(new Double(Math.ceil(pop.size()*untouched)).intValue());
        List<Individual> parents = selector.selectChampions(pop,k,genNumber);
        List<Individual> nextGen = selector.selectChampions(pop,pop.size()-k,genNumber);
        logAverage(parents,"parentsAvg");
        logAverage(nextGen,"survAvg");

        log.finest("Selected champions: " + parents);
        List<Individual> offspring = breeder.breedChampions(parents);
        log.finest("Champions offspring: " + offspring);
        List<Individual> mutatedOffspring = mutator.mutate(offspring);
        log.finest("Mutated offspring: " + mutatedOffspring);
        nextGen.addAll(mutatedOffspring);
        log.finest("End of gen "+genNumber+": " + pop);
        return nextGen;
    }
}
