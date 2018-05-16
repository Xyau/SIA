package individuals;

import genes.Genes;
import genes.IntegerGenotype;
import genes.Species;
import interfaces.Genotype;

import java.util.*;

public class BitsetIndividual extends Individual {
    Genes genes;
    Species species;

    public BitsetIndividual(Random random) {
        species = getSpecies();
        this.genes = species.getRandomGenes(random);
    }

    public BitsetIndividual(Genes genes) {
        this.genes = genes;
    }

    public Genes getGenes() {
        return genes;
    }

    @Override
    public Individual incubate(Genes alteredGenes) {
        return new BitsetIndividual(alteredGenes);
    }

    @Override
    public Individual incubate() {
        return new BitsetIndividual(genes);
    }

    @Override
    public Number getFitness() {
        return genes.getPhenotypeByName("S").getValue() +
                genes.getPhenotypeByName("A").getValue() ;
    }

    public Species getSpecies(){
        List<Genotype> genotypes = new ArrayList<>();
        genotypes.add(new IntegerGenotype(5,15,"S"));
        genotypes.add(new IntegerGenotype(5,15,"A"));
        Species species = new Species("BitSet",genotypes);
        return species;
    }
}
