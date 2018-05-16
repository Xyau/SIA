package mutators;

import individuals.Individual;
import interfaces.Mutator;

import java.util.List;

public class NoChangeMutator implements Mutator {
    @Override
    public List<Individual> mutate(List<Individual> individualsToMutate) {
        return individualsToMutate;
    }
}
