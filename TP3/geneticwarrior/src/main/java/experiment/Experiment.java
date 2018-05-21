package experiment;

import interfaces.Breeder;
import individuals.Individual;
import interfaces.Mutator;
import interfaces.Selector;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Experiment {
    private Logger log= Logger.getGlobal();
    private Breeder breeder;
    private List<Individual> startingPop;
    private Mutator mutator;
    private Selector selector;
    private Integer maxGenerations;

    private List<Double> averageChampionFitnessThroughTime;
    private List<Double> averageFitnessThroughTime;
    private List<Double> maxChampionFitnessThroughTime;

    Experiment( Breeder breeder, List<Individual> startingPop, Mutator mutator,
            Selector selector, Integer maxGenerations) {
        this.breeder = breeder;
        this.startingPop = startingPop;
        this.mutator = mutator;
        this.selector = selector;
        this.maxGenerations = maxGenerations;
        averageChampionFitnessThroughTime = new ArrayList<>();
        averageFitnessThroughTime = new ArrayList<>();
        maxChampionFitnessThroughTime = new ArrayList<>();
    }

    public List<Pair<String,List<Double>>> run(){
        log.info("Starting Experiment");
        List<Individual> pop = new ArrayList<>();
        pop.addAll(startingPop);
        log.info("Starting pop:" + pop);
        List<Individual> champions = startingPop;
        List<Individual> mutatedChampions;
        List<Individual> offspring;
        logStats(pop);
        for (int i = 0; i < maxGenerations; i++) {
            champions = selector.selectChampions(pop);
            logChampionStats(champions);
            log.finest("Selected champions: " + champions);
            offspring = breeder.breedChampions(champions);
            log.finest("Champions offspring: " + offspring);
            mutatedChampions = mutator.mutate(champions);
            log.finest("Mutated champions: " + mutatedChampions);
            pop = champions;
            pop.addAll(mutatedChampions);
            pop.addAll(offspring);
            logStats(pop);
            log.finest("End of round: " + pop);
        }
        champions = selector.selectChampions(pop);
        log.finest("Starting champions: " + startingPop);
        log.info("Final Champions: " + champions);
        List<Pair<String,List<Double>>> timeseries = new ArrayList<>();
        timeseries.add(new Pair<>("max", maxChampionFitnessThroughTime));
        timeseries.add(new Pair<>("avg",averageChampionFitnessThroughTime));
        return timeseries;
    }

    private void logStats(List<Individual> individuals){
         Double average = individuals.stream().map(Individual::getFitness).collect(Collectors.averagingDouble(x->x));
         averageFitnessThroughTime.add(average);
    }

    private void logChampionStats(List<Individual> champions){
        Optional<Double> championsMax = champions.stream().map(Individual::getFitness)
                                                          .max(Comparator.comparingDouble(x->x));
        Double championsAverage = champions.stream().map(Individual::getFitness).collect(Collectors.averagingDouble(x->x));
        averageChampionFitnessThroughTime.add(championsAverage);
        if(championsMax.isPresent()){
            maxChampionFitnessThroughTime.add(championsMax.get());
        } else {
            maxChampionFitnessThroughTime.add(0d);
        }
    }
}
