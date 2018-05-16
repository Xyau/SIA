package individuals;

import genes.Genes;
import genes.Species;

import java.util.function.Supplier;

public abstract class Individual {
    Species species;
    Genes genes;

    public Genes getGenes(){
        return genes;
    }

    //Return a NEW altered version of the individual
    abstract public Individual incubate(Genes alteredGenes);

    //Return a NEW copy of the individual
    abstract public Individual incubate();

    abstract public Number getFitness();

    public Species getSpecies(){
        return species;
    }

    public Number getFitness(Supplier<Number> entries){
        return getFitness();
    }

    public String toString(){
        return getSpecies().getName() +": " + getGenes().toString();
    }
}
