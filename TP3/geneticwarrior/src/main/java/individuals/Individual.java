package individuals;

import genes.Genes;
import genes.Species;

import java.util.function.Supplier;

public abstract class Individual {
    abstract public Genes getGenes();

    //Return a NEW altered version of the individual
    abstract public Individual incubate(Genes alteredGenes);

    //Return a NEW copy of the individual
    abstract public Individual incubate();

    abstract public Number getFitness();

    abstract public Species getSpecies();

    public Number getFitness(Supplier<Number> entries){
        return getFitness();
    }

    public String toString(){
        return getSpecies().getName() +": " + getGenes().toString();
    }
}
