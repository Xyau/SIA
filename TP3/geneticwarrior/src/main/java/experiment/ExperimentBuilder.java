package experiment;

import interfaces.Breeder;
import individuals.Individual;
import interfaces.Mutator;
import interfaces.Selector;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Random;

public class ExperimentBuilder {
    private Breeder breeder;
    private List<Individual> startingPop;
    private Mutator mutator;
    private Selector selector;
    private Selector replacement;
    private String name;

    private ExperimentTypes experimentType;
    private Random random;
    private Integer parentAmount;
    private Double parentRatio;
    private Integer maxGenerations ;

    private Double targetFitness;
    private Integer maxStaleBestFitnessGenerations;
    private Integer maxStaleIndividualsGenerations;

    private enum ExperimentTypes{
        SIMPLE, NORMAL, COMPLEX
    }

    private Logger logger = Logger.getRootLogger();

    public Experiment buildExperiment(){
        if(breeder == null || startingPop == null || mutator == null || selector == null || replacement == null ){
            throw new IllegalStateException("Missing values to instance experiment.Experiment");
        }
        if(maxGenerations == null){
            maxGenerations = 10;
        }
        if(experimentType != ExperimentTypes.SIMPLE) {
            parentAmount = new Double(parentRatio*startingPop.size()).intValue();
            if(parentAmount%2 == 1){
                if(parentAmount >= startingPop.size()){
                    parentAmount --;
                } else {
                    parentAmount ++;
                }
            }
            logger.info("Parent Ratio: " + parentRatio.toString().substring(0,Math.min(parentRatio.toString().length(),3))
                    +   " translates to Parent Amount: " + parentAmount);
        }
        switch (experimentType){
            case SIMPLE:
                return new ExperimentReplacementSimple(name,breeder,startingPop,mutator,selector,replacement,
                        maxGenerations,targetFitness,maxStaleBestFitnessGenerations,maxStaleIndividualsGenerations);
            case NORMAL:
                if(parentAmount > startingPop.size()){
                    throw new IllegalStateException("too many parents to instance");
                }
                return new ExperimentReplacementNormal(name,breeder,startingPop,mutator,selector,replacement,
                        maxGenerations,targetFitness,maxStaleBestFitnessGenerations,maxStaleIndividualsGenerations,
                        parentAmount,random);
            default:
            case COMPLEX:
                if(parentAmount > startingPop.size()){
                    throw new IllegalStateException("too many parents to instance");
                }
                return new ExperimentReplacementComplex(name,breeder,startingPop,mutator,selector,replacement,
                        maxGenerations,targetFitness,maxStaleBestFitnessGenerations,maxStaleIndividualsGenerations,
                        parentAmount,random);
        }
    }

    public ExperimentBuilder addBreeder(Breeder breeder){
        this.breeder = breeder;
        return this;
    }
    public ExperimentBuilder addSelector(Selector selector){
        this.selector = selector;
        return this;
    }
    public ExperimentBuilder addReplacement(Selector replacement){
        this.replacement = replacement;
        return this;
    }
    public ExperimentBuilder addMutator(Mutator mutator){
        this.mutator = mutator;
        return this;
    }
    public ExperimentBuilder addStartingPop(List<Individual> startingPop){
        this.startingPop = startingPop;
        if(startingPop.size() < 2) throw new IllegalArgumentException("popsize too small");
        return this;
    }
    public ExperimentBuilder addMaxGenerations(Integer maxGenerations){
        this.maxGenerations = maxGenerations;
        return this;
    }

    public ExperimentBuilder addName(String name) {
        this.name = name;
        return this;
    }
    public ExperimentBuilder replacementSimple(){
        experimentType = ExperimentTypes.SIMPLE;
        return this;
    }

    public ExperimentBuilder replacementComplex(Double parentRatio, Random random){
        experimentType = ExperimentTypes.COMPLEX;
        this.parentRatio = parentRatio;
        this.random = random;
        return this;
    }

    public ExperimentBuilder replacementNormal(Double parentRatio, Random random){
        experimentType = ExperimentTypes.NORMAL;
        this.parentRatio = parentRatio;
        this.random = random;
        return this;
    }

    public ExperimentBuilder addTargetFitness(Double targetFitness){
        this.targetFitness = targetFitness;
        return this;
    }

    public ExperimentBuilder addmaxStaleBestFitnessGenerations(Integer maxStaleBestFitnessGenerations){
        this.maxStaleBestFitnessGenerations = maxStaleBestFitnessGenerations;
        return this;
    }

    public ExperimentBuilder addMaxStaleIndividuals(Integer maxStaleIndividualsGenerations){
        this.maxStaleIndividualsGenerations = maxStaleIndividualsGenerations;
        return this;
    }
}
