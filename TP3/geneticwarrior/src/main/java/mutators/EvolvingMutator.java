package mutators;

import genes.Genes;
import genes.Species;
import individuals.Individual;
import interfaces.Mutator;
import interfaces.Phenotype;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EvolvingMutator implements Mutator {
    private Random random;
    private Function<Integer,Double> evaluator;

    public EvolvingMutator(Double startRatio, Double endRatio, Integer duration, Random random) {
        this.random = random;
        this.evaluator = (generation ->
                startRatio * (1 - ((Math.max(generation.doubleValue(), duration) / duration)) +
                        endRatio * (Math.max(generation.doubleValue(), duration) / duration)));
    }
    @Override
    public List<Individual> mutate(List<Individual> individualsToMutate,Integer genNumber) {
        return individualsToMutate.stream()
                .map( individual -> (random.nextDouble() < evaluator.apply(genNumber))?mutate(individual):individual)
                .collect(Collectors.toList());
    }

    private Individual mutate(Individual individual){
        Genes genes = individual.getGenes();
        Species species = individual.getSpecies();
        List<Phenotype> mutatedPhenotypes = species.getGenotypes().stream().map(genotype ->
                genotype.getMutation(genes.getPhenotypeByName(genotype.getName()),
                        (random.nextDouble()*2-1)))
                .collect(Collectors.toList());
        Genes mutatedGenes = new Genes(mutatedPhenotypes);
        return individual.incubate(mutatedGenes);
    }
}
