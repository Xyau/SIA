package breeders;

import individuals.BitsetIndividual;
import individuals.Individual;
import interfaces.Mutator;
import mutators.SimpleMutator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MutatorTest {
    @Test
    public void SimpleCrossBreederTest(){
        Random random = new Random(1011);
        Mutator mutator = new SimpleMutator(0.5d,3d,random);
        List<Individual> startingPop = new ArrayList<>();

        startingPop.add(new BitsetIndividual(random));
        startingPop.add(new BitsetIndividual(random));
        System.out.println(startingPop);

        List<Individual> offspring = mutator.mutate(startingPop);
        System.out.println(offspring);
    }
}
