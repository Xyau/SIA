package experiment;

import interfaces.Breeder;
import individuals.Individual;
import interfaces.Mutator;
import interfaces.Selector;

import java.util.List;
import java.util.Random;

public class ExperimentBuilder {
    private Breeder breeder;
    private List<Individual> startingPop;
    private Mutator mutator;
    private Selector selector;
    private Integer maxGenerations;
    private Integer workingPop;
    private String name;

    private ExperimentTypes experimentType;
    private Double untouchedRatio;
    private Random random;

    private enum ExperimentTypes{
        SIMPLE, NORMAL, COMPLEX
    }
    public Experiment buildExperiment(){
        if(breeder == null || startingPop == null || mutator == null || selector == null ){
            throw new IllegalStateException("Missing values to instance experiment.Experiment");
        }
        if(maxGenerations == null){
            maxGenerations = 10;
        }
        if(workingPop == null){
            workingPop = startingPop.size();
        }
        switch (experimentType){
            case SIMPLE:
                return new ExperimentReplacementSimple(breeder,startingPop,mutator,selector,maxGenerations,workingPop,name);
            case NORMAL:
                return new ExperimentReplacementNormal(breeder,startingPop,mutator,selector,maxGenerations,workingPop,name,untouchedRatio,random);
            case COMPLEX:
                default:
                return new ExperimentReplacementComplex(breeder,startingPop,mutator,selector,maxGenerations,workingPop,name,untouchedRatio,random);
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
    public ExperimentBuilder addMutator(Mutator mutator){
        this.mutator = mutator;
        return this;
    }
    public ExperimentBuilder addStartingPop(List<Individual> startingPop){
        this.startingPop = startingPop;
        return this;
    }
    public ExperimentBuilder addMaxGenerations(Integer maxGenerations){
        this.maxGenerations = maxGenerations;
        return this;
    }

    public ExperimentBuilder addWorkingPop(Integer workingPop) {
        this.workingPop = workingPop;
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

    public ExperimentBuilder replacementComplex(Double untouchedRatio, Random random){
        experimentType = ExperimentTypes.COMPLEX;
        this.untouchedRatio = untouchedRatio;
        this.random = random;
        return this;
    }

    public ExperimentBuilder replacementNormal(Double untouchedRatio, Random random){
        experimentType = ExperimentTypes.NORMAL;
        this.untouchedRatio = untouchedRatio;
        this.random = random;
        return this;
    }
}
