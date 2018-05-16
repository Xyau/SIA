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
    Genes genes;
    Species species;

    public BitsetIndividual(Random random) {
        species = generateSpecies();
        this.genes = species.getRandomGenes(random);
    }

    private Species generateSpecies(){
        List<Genotype> genotypes = new ArrayList<>();
        genotypes.add(new IntegerGenotype(5,15,"S"));
        genotypes.add(new IntegerGenotype(0,10,"A"));
        Species species = new Species("BitSet",genotypes);
        return species;
    }

    @Override
    public Number getFitness() {
        return genes.getPhenotypeByName("S").getValue("") +
                genes.getPhenotypeByName("A").getValue("") ;
    }


    public BitsetIndividual(Genes genes) {
        this.genes = genes;
    }

    @Override
    public Individual incubate(Genes alteredGenes) {
        return new BitsetIndividual(alteredGenes);
    }

    @Override
    public Individual incubate() {
        return new BitsetIndividual(genes);
    }

}
