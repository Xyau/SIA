package breeders;

import genes.Species;
import individuals.Individual;
import individuals.Warrior;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WarriorTest {
    @Test
    public void test(){
        Species species = Warrior.generateSpecies();
        List<Individual> startingPop = new ArrayList<>();
        Random random = new Random();
        startingPop.add(new Warrior(species,random));
        startingPop.add(new Warrior(species,random));

        System.out.println(species);
    }
}
