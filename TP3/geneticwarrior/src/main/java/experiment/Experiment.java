package experiment;

import interfaces.Breeder;
import individuals.Individual;
import interfaces.Mutator;
import interfaces.Selector;

import java.util.List;
import java.util.logging.Logger;

public class Experiment {
    private Logger log= Logger.getGlobal();
    private Breeder breeder;
    private List<Individual> startingPop;
    private Mutator mutator;
    private Selector selector;
    private Integer maxGenerations;


     Experiment(Breeder breeder, List<Individual> startingPop, Mutator mutator,
                Selector selector, Integer maxGenerations) {
        this.breeder = breeder;
        this.startingPop = startingPop;
        this.mutator = mutator;
        this.selector = selector;
        this.maxGenerations = maxGenerations;
    }

    public Individual run(){
        log.info("Starting Experiment");
        List<Individual> pop = startingPop;
        log.info("Starting pop:" + pop);
        List<Individual> champions = startingPop;
        List<Individual> mutatedChampions;
        List<Individual> offspring;
        for (int i = 0; i < maxGenerations; i++) {
            champions = selector.selectChampions(pop);
            log.info("Selected champions: " + champions);
            mutatedChampions = mutator.mutate(champions);
            log.info("Mutated champions: " + mutatedChampions);
            offspring = breeder.breedChampions(mutatedChampions);
            log.info("Champions offspring: " + offspring);
            pop = offspring;
            pop.addAll(mutatedChampions);
            log.info("End of round: " + pop);
        }

        return champions.get(0);
    }
}
