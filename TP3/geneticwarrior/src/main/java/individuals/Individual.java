package individuals;

import genes.Genes;
import genes.Species;

import java.util.Objects;
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

    abstract public Double getFitness();

    public Species getSpecies(){
        return species;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual that = (Individual) o;
        return Objects.equals(species, that.species) &&
                Objects.equals(genes, that.genes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(species, genes);
    }

    public Number getFitness(Supplier<Number> entries){
        return getFitness();
    }

    public String toString(){
        return getSpecies().getName() +": " + getGenes().toString();
    }
}
