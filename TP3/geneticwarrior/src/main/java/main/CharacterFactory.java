package main;

import genes.Species;
import individuals.Character;
import individuals.Individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.logging.Logger;

public class CharacterFactory {
    BiFunction<Double, Double, Double> warriorFitness = (attack,defense)->0.6D*attack+0.4D*defense;
    BiFunction<Double, Double, Double> archerFitness = (attack,defense)->0.9D*attack+0.1D*defense;
    BiFunction<Double, Double, Double> defenderFitness = (attack,defense)->0.1D*attack+0.9*defense;
    BiFunction<Double, Double, Double> assasinFitness = (attack,defense)->0.7D*attack+0.3D*defense;

    Species species = Character.generateSpecies();

    public List<Individual> createRandomWarrior(Integer variant, Random random, Integer amount){
        List<Individual> pop = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            pop.add(createRandomWarrior(variant,species,random));
        }
        return pop;
    }
    public Individual createRandomWarrior(Integer variant, Species species, Random random){
        switch (variant){
            case 1:
                return new Character(species,random,"warrior 1",1.1D,0.9D,0.8D,1.0D,0.9D,warriorFitness);
            case 2:
                return new Character(species,random,"warrior 2",1.2D,1.0D,0.8D,0.8D,0.8D,warriorFitness);
            case 3:
                return new Character(species,random,"warrior 3",0.8D,0.9D,0.9D,1.2D,1.1D,warriorFitness);
            default:
                Logger.getGlobal().warning("No such warrior variant: "+variant);
        }
        return null;
    }

    public List<Individual> createRandomArcher(Integer variant, Random random, Integer amount){
        List<Individual> pop = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            pop.add(createRandomArcher(variant,species,random));
        }
        return pop;
    }
    public Character createRandomArcher(Integer variant, Species species, Random random){
        switch (variant){
            case 1:
                return new Character(species,random,"archer 1",0.8D,1.1D,1.1D,0.9D,0.7D,archerFitness);
            case 2:
                return new Character(species,random,"archer 2",0.9D,1.1D,1D,0.9D,0.9D,archerFitness);
            case 3:
                return new Character(species,random,"archer 3",0.8D,0.8D,0.8D,1.1D,1.2D,archerFitness);
            default:
                Logger.getGlobal().warning("No such archer variant: "+variant);
        }
        return null;
    }

    public List<Individual> createRandomDefender(Integer variant, Random random, Integer amount){
        List<Individual> pop = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            pop.add(createRandomDefender(variant,species,random));
        }
        return pop;
    }
    public Character createRandomDefender(Integer variant, Species species, Random random){
        switch (variant){
            case 1:
                return new Character(species,random,"defender 1",1.0D,0.9D,0.7D,1.2D,1.1D,defenderFitness);
            case 2:
                return new Character(species,random,"defender 2",1.1D,0.8D,0.8D,1.1D,1.1D,defenderFitness);
            case 3:
                return new Character(species,random,"defender 3",0.9D,0.9D,0.9D,1.0D,1.3D,defenderFitness);
            default:
                Logger.getGlobal().warning("No such defender variant: "+variant);
        }
        return null;
    }

    public List<Individual> createRandomAssasin(Integer variant, Random random, Integer amount){
        List<Individual> pop = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            pop.add(createRandomAssasin(variant,species,random));
        }
        return pop;
    }

    public Character createRandomAssasin(Integer variant, Species species, Random random){
        switch (variant){
            case 1:
                return new Character(species,random,"assasin 1",0.8D,1.2D,1.1D,1.0D,0.8D,assasinFitness);
            case 2:
                return new Character(species,random,"assasin 2",0.9D,1.0D,1.1D,1.0D,0.9D,assasinFitness);
            case 3:
                return new Character(species,random,"assasin 3",0.9D,0.9D,1.0D,1.1D,1.0D,assasinFitness);
            default:
                Logger.getGlobal().warning("No such defender variant: "+variant);
        }
        return null;
    }
}
