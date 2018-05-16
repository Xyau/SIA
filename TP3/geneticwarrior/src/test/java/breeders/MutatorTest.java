package breeders;

import individuals.BitsetIndividual;
import individuals.Individual;
import interfaces.Mutator;
import mutators.SimpleBitMutator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MutatorTest {
    @Test
    public void SimpleCrossBreederTest(){
        Random random = new Random();
        Mutator mutator = new SimpleBitMutator(0d,random);
        List<Individual> startingPop = new ArrayList<>();
        startingPop.add(new BitsetIndividual(random));
        System.out.println(startingPop);

        List<Individual> offspring = mutator.mutate(startingPop);
        System.out.println(offspring);
    }
}
