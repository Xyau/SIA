package breeders;

import genes.Species;
import individuals.Character;
import individuals.Individual;
import main.CharacterFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WarriorTest {
    @Test
    public void test(){
        Species species = Character.generateSpecies(new Random());
        List<Individual> startingPop = new ArrayList<>();
        Random random = new Random();
        CharacterFactory characterFactory = new CharacterFactory(new Random());
        startingPop.addAll(characterFactory.createRandomWarrior(2,random,3));

        System.out.println(species);
    }
}
