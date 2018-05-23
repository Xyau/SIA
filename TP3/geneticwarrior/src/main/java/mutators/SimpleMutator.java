package mutators;

import genes.Genes;
import genes.Species;
import individuals.Individual;
import interfaces.Mutator;
import interfaces.Phenotype;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SimpleMutator implements Mutator {
    private Double mutationRatio;
    private Double mutationStrenght;
    private Random random;

    public SimpleMutator(Double mutationRatio,Double mutationStrenght, Random random) {
        if(mutationRatio > 1 || mutationRatio < 0) {
            throw new IllegalArgumentException("Mutation ratio must be between 0 and 1");
        }
        if(mutationStrenght < 0) {
            throw new IllegalArgumentException("mutation strenght must be >0");
        }
        this.mutationRatio = mutationRatio;
        this.mutationStrenght = mutationStrenght;
        this.random = random;
    }

    @Override
    public List<Individual> mutate(List<Individual> individualsToMutate) {
        return individualsToMutate.stream()
                .map( individual -> (random.nextDouble() < mutationRatio)?mutate(individual):individual)
                .collect(Collectors.toList());
    }

    private Individual mutate(Individual individual){
        Genes genes = individual.getGenes();
        Species species = individual.getSpecies();
        List<Phenotype> mutatedPhenotypes = species.getGenotypes().stream().map(genotype ->
                genotype.getMutation(genes.getPhenotypeByName(genotype.getName()),
                        (random.nextDouble()*2-1)*mutationStrenght))
                .collect(Collectors.toList());
        Genes mutatedGenes = new Genes(mutatedPhenotypes);
        return individual.incubate(mutatedGenes);
    }
}
