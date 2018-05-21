package experiment;

import interfaces.Breeder;
import individuals.Individual;
import interfaces.Mutator;
import interfaces.Selector;
import javafx.util.Pair;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

abstract public class Experiment {
    private List<Individual> startingPop;
    private Integer maxGenerations;

    Logger log= Logger.getGlobal();
    Breeder breeder;
    Mutator mutator;
    Selector selector;
    Integer workingPop;

    Map<String, List<Double>> timeseries;

    private String name;
    private List<Double> averageChampionFitnessThroughTime;
    private List<Double> averageFitnessThroughTime;
    private List<Double> maxChampionFitnessThroughTime;

    Experiment( Breeder breeder, List<Individual> startingPop, Mutator mutator,
            Selector selector, Integer maxGenerations, Integer workingPop, String name) {
        this.breeder = breeder;
        this.startingPop = startingPop;
        this.mutator = mutator;
        this.selector = selector;
        this.maxGenerations = maxGenerations;
        this.workingPop = workingPop;
        this.name = name;
        timeseries = new HashMap<>();
        averageChampionFitnessThroughTime = new ArrayList<>();
        averageFitnessThroughTime = new ArrayList<>();
        maxChampionFitnessThroughTime = new ArrayList<>();
    }

    public Map<String, List<Double>> run(){
        log.info("Starting Experiment");
        List<Individual> pop = new ArrayList<>();
        pop.addAll(startingPop);
        log.info("Starting pop:" + startingPop);
        List<Individual> nextGen=new ArrayList<>();
        logAverage(startingPop,"average");
        for (int i = 0; i < maxGenerations; i++) {
            nextGen = makeNextGeneration(pop,i);
            logAverage(nextGen,"average");
        }
        log.finest("Starting champions: " + startingPop);
        log.info("Final Champions: " + nextGen);
        return timeseries;
    }

    abstract List<Individual> makeNextGeneration(List<Individual> pop,Integer genNumber);

    void logAverage(List<Individual> individuals, String seriesName){
         Double average = individuals.stream().map(Individual::getFitness).collect(Collectors.averagingDouble(x->x));
         addEntryToTimeseries(seriesName,average);
    }

    void addEntryToTimeseries(String timeseriesName, Double value){
        timeseriesName = name + " " + timeseriesName;
        List<Double> seriesList = timeseries.get(timeseriesName);
        if(seriesList == null){
            seriesList = new ArrayList<>();
            seriesList.add(value);
            timeseries.put(timeseriesName,seriesList);
        } else {
            seriesList.add(value);
        }
    }
}
