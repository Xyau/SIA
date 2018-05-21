package experiment;

import interfaces.Breeder;
import individuals.Individual;
import interfaces.Mutator;
import interfaces.Selector;

import java.util.List;

public class ExperimentBuilder {
    private Breeder breeder;
    private List<Individual> startingPop;
    private Mutator mutator;
    private Selector selector;
    private Integer maxGenerations;
    private Integer workingPop;

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
        return new Experiment(breeder,startingPop,mutator,selector,maxGenerations,workingPop);
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
}
