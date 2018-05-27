package individuals;

import genes.Genes;
import genes.IntegerGenotype;
import genes.Species;
import interfaces.Genotype;
import utils.Utils;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BitsetIndividual extends Individual {
    public BitsetIndividual(Random random) {
        species = generateSpecies();
        this.genes = species.getRandomGenes(random);
    }

    private BitsetIndividual(Species species,Genes genes) {
        this.species = species;
        this.genes = genes;
    }

    private Species generateSpecies(){
        List<Genotype> genotypes = new ArrayList<>();
        genotypes.add(new IntegerGenotype(5,15,"S"));
        genotypes.add(new IntegerGenotype(0,10,"A"));
        Species species = new Species("BitSet",genotypes);
        return species;
    }

    @Override
    public Double getFitness() {
        return 1.0* genes.getPhenotypeByName("S").getValue("") +
                genes.getPhenotypeByName("A").getValue("") ;
    }


    @Override
    public Individual incubate(Genes alteredGenes) {
        return new BitsetIndividual(species,alteredGenes);
    }
}
