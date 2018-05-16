package mutators;

import genes.Genes;
import genes.Species;
import individuals.Individual;
import interfaces.Mutator;
import interfaces.Phenotype;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SimpleBitMutator implements Mutator {
    Double mutationRatio;
    Random random;

    public SimpleBitMutator(Double mutationRatio, Random random) {
        this.mutationRatio = mutationRatio;
        this.random = random;
    }

    @Override
    public List<Individual> mutate(List<Individual> individualsToMutate) {
        return individualsToMutate.stream().map( individual -> mutate(individual)).collect(Collectors.toList());
    }

    private Individual mutate(Individual individual){
        Genes genes = individual.getGenes();
        Species species = individual.getSpecies();
        List<Phenotype> mutatedPhenotypes = species.getGenotypes().stream().map(genotype ->
                genotype.getMutation(genes.getPhenotypeByName(genotype.getName()),
                        random.nextDouble()*2-1)).collect(Collectors.toList());
        Genes mutatedGenes = new Genes(mutatedPhenotypes);
        return individual.incubate(mutatedGenes);
    }
}
