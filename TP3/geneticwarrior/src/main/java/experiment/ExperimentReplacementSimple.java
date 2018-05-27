package experiment;

import individuals.Individual;
import interfaces.Breeder;
import interfaces.Mutator;
import interfaces.Selector;

import java.util.ArrayList;
import java.util.List;

public class ExperimentReplacementSimple extends Experiment {
    ExperimentReplacementSimple(String name, Breeder breeder, List<Individual> startingPop, Mutator mutator, Selector selector,
                                Selector replacement, Integer maxGenerations) {
        super(name,breeder, startingPop, mutator, selector, replacement,maxGenerations);
    }

    @Override
    List<Individual> makeNextGeneration(List<Individual> pop, Integer genNumber) {
        List<Individual> nextGen = new ArrayList<>();
        List<Individual> genParents = new ArrayList<>();
        while (nextGen.size() < pop.size()){
            List<Individual> parents = selector.selectChampions(pop,2,genNumber);
            genParents.addAll(parents);
            log.finest("Selected champions: " + parents);
            List<Individual> offspring = breeder.breedChampions(parents);
            log.finest("Champions offspring: " + offspring);
            List<Individual> mutatedOffspring = mutator.mutate(offspring);
            log.finest("Mutated offspring: " + mutatedOffspring);
            nextGen.addAll(mutatedOffspring);
        }

        logAverage(genParents,"parentsAvg");
        log.finest("End of gen "+genNumber+": " + pop);
        return nextGen;
    }
}
