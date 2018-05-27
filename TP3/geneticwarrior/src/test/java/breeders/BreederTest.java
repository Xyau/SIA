package breeders;

import individuals.BitsetIndividual;
import interfaces.Breeder;
import individuals.Individual;
import main.CharacterFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BreederTest {

    @Test
    public void SimpleCrossBreederTest(){
        Random random = new Random();
        Breeder breeder = new SimpleCrossBreeder(new Random(),0.7f);
        List<Individual> startingPop = new ArrayList<>();
        startingPop.add(new BitsetIndividual(random));
        startingPop.add(new BitsetIndividual(random));

        System.out.println(startingPop);
        List<Individual> offspring = breeder.breedChampions(startingPop);
        System.out.println(offspring);
    }
    @Test
    public void CrossoverBreederTest(){
        Random random = new Random();
        Breeder breeder = new AnularBreeder(new Random(),0.7f);
        CharacterFactory characterFactory = new CharacterFactory();
        List<Individual> startingPop = characterFactory.createRandomAssasin(1,random,2);

        System.out.println(startingPop);
        List<Individual> offspring = breeder.breedChampions(startingPop);
        System.out.println(offspring);
    }
}
