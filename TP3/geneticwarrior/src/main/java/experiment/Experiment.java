package experiment;

import interfaces.Breeder;
import individuals.Individual;
import interfaces.Mutator;
import interfaces.Selector;

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
    Selector reeplacement;
    Integer workingPop;

    Map<String, List<Double>> timeseries;

    private String name;

    Experiment( String name, Breeder breeder, List<Individual> startingPop, Mutator mutator,
            Selector selector, Selector replacement, Integer maxGenerations) {
        this.breeder = breeder;
        this.startingPop = startingPop;
        this.mutator = mutator;
        this.selector = selector;
        this.reeplacement = replacement;
        this.maxGenerations = maxGenerations;
        this.name = name;
        timeseries = new HashMap<>();
    }

    public Map<String, List<Double>> run(){
        log.info("Starting Experiment");
        List<Individual> pop = new ArrayList<>();
        pop.addAll(startingPop);
        log.info("Starting pop:" + startingPop);
        List<Individual> nextGen=new ArrayList<>();
        nextGen.addAll(startingPop);
        for (int i = 0; i < maxGenerations; i++) {
            logAverage(nextGen,"average");
            logMax(nextGen,"max");
            nextGen = makeNextGeneration(pop,i);
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

    void logMax(List<Individual> individuals, String seriesName){
         Double max = individuals.stream().map(Individual::getFitness).max(Comparator.comparingDouble(x->x)).get();
         addEntryToTimeseries(seriesName,max);
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
