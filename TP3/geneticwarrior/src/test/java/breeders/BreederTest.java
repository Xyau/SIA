package breeders;

import individuals.BitsetIndividual;
import interfaces.Breeder;
import individuals.Individual;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BreederTest {
    @Test
    public void SimpleCrossBreederTest(){
        Random random = new Random();
        Breeder breeder = new SimpleCrossBreeder(new Random());
        List<Individual> startingPop = new ArrayList<>();
        startingPop.add(new BitsetIndividual(random));
        startingPop.add(new BitsetIndividual(random));

        System.out.println(startingPop);
        List<Individual> offspring = breeder.breedChampions(startingPop);
        System.out.println(offspring);
    }
}
