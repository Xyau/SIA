package experiment;

import interfaces.Breeder;
import individuals.Individual;
import interfaces.Mutator;
import interfaces.Phenotype;
import interfaces.Selector;
import main.ItemType;
import main.NoireChart;
import org.apache.log4j.Logger;
import org.jfree.ui.RefineryUtilities;

import javax.print.attribute.DocAttributeSet;
import java.util.*;
import java.util.stream.Collectors;

abstract public class Experiment {
    private List<Individual> startingPop;
    private Integer maxGenerations;

    Logger log= Logger.getRootLogger();
    Breeder breeder;
    Mutator mutator;
    Selector selector;
    Selector reeplacement;

    private Double targetFitness;
    private Integer maxStaleBestFitnessGenerations;
    private Integer maxStaleIndividualsGenerations;

    private Individual maxInd;
    Map<String, List<Double>> timeseries;

    private String name;
    NoireChart chart;

    Experiment( String name, Breeder breeder, List<Individual> startingPop, Mutator mutator,
            Selector selector, Selector replacement, Integer maxGenerations, Double targetFitness,
                Integer maxStaleBestFitnessGenerations, Integer maxStaleIndividualsGenerations) {
        this.breeder = breeder;
        Collections.sort(startingPop,Comparator.comparingDouble(Individual::getFitness));
        Collections.reverse(startingPop);
        this.startingPop = startingPop;
        this.mutator = mutator;
        this.selector = selector;
        this.reeplacement = replacement;
        this.maxGenerations = maxGenerations;
        this.name = name;
        this.targetFitness = targetFitness;
        this.maxStaleBestFitnessGenerations = maxStaleBestFitnessGenerations;
        this.maxStaleIndividualsGenerations = maxStaleIndividualsGenerations;
        timeseries = new HashMap<>();
        chart = new NoireChart(name,
                "");
        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }

    public Map<String, List<Double>> run(){
        log.info("Starting " + name);
        List<Individual> pop = new ArrayList<>();
        pop.addAll(startingPop);
        log.info(name + " starting pop:" + startingPop);
        List<Individual> nextGen=new ArrayList<>();
        nextGen.addAll(startingPop);
        for (int i = 0; !shouldCutSimulation(i); ) {
            nextGen = i==0?nextGen:makeNextGeneration(nextGen,i);
            logAverage(nextGen,"average");
            logMax(nextGen,"max");
            logDifferentGenes(nextGen,"diversity");
            logClones(nextGen,"clones");
            printChart(i,nextGen);
            log.info(name + " finished gen:" + (i+1));
            i++;
        }
        log.info(name + "starting champions: " + startingPop);
        Collections.sort(nextGen,Comparator.comparingDouble(Individual::getFitness));
        Collections.reverse(nextGen);
        log.info(name + "final Champions: " + nextGen);
        log.info("Best config: " + maxInd);
        maxInd.getGenes().getAllPhenotypes().stream().forEach(phenotype -> log.info(phenotype));

        return timeseries;
    }

    abstract List<Individual> makeNextGeneration(List<Individual> pop,Integer genNumber);

    void logAverage(List<Individual> individuals, String seriesName){
         Double average = individuals.stream().map(Individual::getFitness).collect(Collectors.averagingDouble(x->x));
         addEntryToTimeseries(seriesName,average);
    }

    void logMax(List<Individual> individuals, String seriesName){
         Individual ind = individuals.stream().max(Comparator.comparingDouble(Individual::getFitness)).get();
         maxInd = (maxInd==null||(ind.getFitness()>maxInd.getFitness()))?ind:maxInd;
         addEntryToTimeseries(seriesName,ind.getFitness());
    }

    void logDifferentGenes(List<Individual> individuals, String seriesName){
        Long differentPhenotypes = individuals.parallelStream()
                .flatMap(individual -> individual.getGenes().getAllPhenotypes().stream())
                .collect(Collectors.toSet()).parallelStream().count();
        Integer totalPhenotypes = individuals.size() * individuals.get(0).getGenes().getAllPhenotypes().size();
        addEntryToTimeseries(seriesName,(differentPhenotypes*1d)/totalPhenotypes);
    }

    void logClones(List<Individual> individuals, String seriesName){
        Long distinct = individuals.stream().distinct().count();
        addEntryToTimeseries(seriesName,(individuals.size()-distinct)*1d);
    }

    boolean shouldCutSimulation(Integer genNumber){
        if(genNumber > maxGenerations){
            log.info("Stopping simulation by max generations");
            return true;
        }
        if(isMaxFitnessStale()){
            log.info("Stopping simulation because the max fitness is stale");
            return true;
        }
        List<Double> maxes = timeseries.get(name + " max");
        if(targetFitness != null && (genNumber > 0) && maxes.get(maxes.size()-1)>= targetFitness){
            log.info("Stopping simulation target fitness has been reached");
            return true;
        }
        if(isPopStale()){
            log.info("Stopping simulation because the population is stale");
            return true;
        }

        return false;
    }

    boolean isMaxFitnessStale(){
        List<Double> maxes = timeseries.get(name + " max");
        if(maxes == null || maxStaleBestFitnessGenerations == null || maxes.size() < maxStaleBestFitnessGenerations){
            return false;
        }
        Double max = maxes.get(maxes.size()-1);
        for (int i = maxes.size()-1; i >= Math.max(0,maxes.size()-1-maxStaleBestFitnessGenerations) ; i--) {
            if(delta(max,maxes.get(i))>0.001){
                return false;
            }
        }
        return true;
    }

    boolean isPopStale(){
        List<Double> clones = timeseries.get(name + " clones");
        if(clones == null ||  maxStaleIndividualsGenerations == null || clones.size() < maxStaleIndividualsGenerations){
            return false;
        }
        Double max = clones.get(clones.size()-1);
        for (int i = clones.size()-1; i >= Math.max(0,clones.size()-1-maxStaleIndividualsGenerations) ; i--) {
            if(delta(clones.get(i),max)>0.001){
                return false;
            }
        }
        return true;
    }

    public static double delta(double d1, double d2) {
        return Math.abs(d1- d2) / Math.max(Math.abs(d1), Math.abs(d2));
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

    protected void printChart(Integer genNumber, List<Individual> pop) {
        Double max = pop.stream().map(Individual::getFitness).max(Comparator.comparingDouble(x->x)).get();
        Double average = pop.stream().map(Individual::getFitness).collect(Collectors.averagingDouble(x->x));
        Double min = pop.stream().map(Individual::getFitness).min(Comparator.comparingDouble(x->x)).get();
        chart.updateChart(genNumber,max,min,average);
    }
}
