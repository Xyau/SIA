package interfaces;

import individuals.Individual;

import java.util.List;

public interface Mutator {
    public List<Individual> mutate(List<Individual> individualsToMutate);
}
