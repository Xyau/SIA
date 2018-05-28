package experiment;

import individuals.Individual;
import interfaces.Breeder;
import interfaces.Mutator;
import interfaces.Selector;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExperimentReplacementSimple extends Experiment {
    ExperimentReplacementSimple(String name, Breeder breeder, List<Individual> startingPop, Mutator mutator,
                                Selector selector, Selector replacement, Integer maxGenerations, Double targetFitness,
                                Integer maxStaleBestFitnessGenerations, Integer maxStaleIndividualsGenerations) {
        super(name, breeder, startingPop, mutator, selector, replacement, maxGenerations, targetFitness,
                maxStaleBestFitnessGenerations, maxStaleIndividualsGenerations);
    }

    @Override
    List<Individual> makeNextGeneration(List<Individual> pop, Integer genNumber) {
        List<Individual> nextGen = new ArrayList<>();
        List<Individual> genParents = new ArrayList<>();
        while (nextGen.size() < pop.size()){
            List<Individual> parents = selector.selectChampions(pop,2,genNumber);
            genParents.addAll(parents);
            log.debug("Selected champions: " + parents);
            List<Individual> offspring = breeder.breedChampions(parents);
            log.debug("Champions offspring: " + offspring);
            List<Individual> mutatedOffspring = mutator.mutate(offspring,genNumber);
            log.debug("Mutated offspring: " + mutatedOffspring);
            nextGen.addAll(mutatedOffspring);
        }

        logAverage(genParents,"parentsAvg");
        log.debug("End of gen "+genNumber+": " + pop);
        return nextGen;
    }
}
