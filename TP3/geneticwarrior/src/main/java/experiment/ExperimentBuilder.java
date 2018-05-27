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
    private Selector replacement;
    private Integer maxGenerations;
    private String name;

    private ExperimentTypes experimentType;
    private Random random;
    private Integer parentAmount;

    private enum ExperimentTypes{
        SIMPLE, NORMAL, COMPLEX
    }
    public Experiment buildExperiment(){
        if(breeder == null || startingPop == null || mutator == null || selector == null || replacement == null ){
            throw new IllegalStateException("Missing values to instance experiment.Experiment");
        }
        if(maxGenerations == null){
            maxGenerations = 10;
        }
        switch (experimentType){
            case SIMPLE:
                return new ExperimentReplacementSimple(name,breeder,startingPop,mutator,selector,replacement,maxGenerations);
            case NORMAL:
                if(parentAmount > startingPop.size()){
                    throw new IllegalStateException("too many parents to instance");
                }
                return new ExperimentReplacementNormal(name,breeder,startingPop,mutator,selector,replacement,maxGenerations,parentAmount,random);
            default:
            case COMPLEX:
                if(parentAmount > startingPop.size()){
                    throw new IllegalStateException("too many parents to instance");
                }
                return new ExperimentReplacementComplex(name,breeder,startingPop,mutator,selector,replacement,maxGenerations,parentAmount,random);
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

    public ExperimentBuilder replacementComplex(Integer parentAmount, Random random){
        experimentType = ExperimentTypes.COMPLEX;
        this.parentAmount = parentAmount;
        this.random = random;
        return this;
    }

    public ExperimentBuilder replacementNormal(Integer parentAmount, Random random){
        if(parentAmount%2 == 1){
            parentAmount ++;
        }
        experimentType = ExperimentTypes.NORMAL;
        this.parentAmount = parentAmount;
        this.random = random;
        return this;
    }
}
