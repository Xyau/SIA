package individuals;

import genes.Genes;
import genes.IntegerGenotype;
import genes.ItemGenotype;
import genes.Species;
import interfaces.Genotype;
import interfaces.Phenotype;
import main.BonusType;
import main.ItemType;
import utils.TSVReader;
import utils.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Warrior extends Individual {
    Double fitness;

    public Warrior(Species species,Random random) {
        this.species = species;
        this.genes = species.getRandomGenes(random);
    }

    public Warrior(Species species, Genes genes) {
        this.species = species;
        this.genes = genes;
    }

    public static Species generateSpecies(){
        List<Genotype> genotypes = new ArrayList<>();
        genotypes.add(new ItemGenotype(ItemType.HELMET,TSVReader.parseFile(ItemType.HELMET)));
        genotypes.add(new ItemGenotype(ItemType.ARMOR,TSVReader.parseFile(ItemType.ARMOR)));
        genotypes.add(new ItemGenotype(ItemType.GLOVES,TSVReader.parseFile(ItemType.GLOVES)));
        genotypes.add(new ItemGenotype(ItemType.BOOTS,TSVReader.parseFile(ItemType.BOOTS)));
        genotypes.add(new ItemGenotype(ItemType.WEAPON,TSVReader.parseFile(ItemType.WEAPON)));
        genotypes.add(new IntegerGenotype(13,20,"height"));
        Species species = new Species("Warrior",genotypes);
        return species;
    }

    public static List<Individual> generateIndividuals(Species species,Integer amount,Random random) {
        List<Individual> pop = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            pop.add(new Warrior(species,species.getRandomGenes(random)));
        }
        return pop;
    }

    @Override
    public Individual incubate(Genes alteredGenes) {
        return new Warrior(species,alteredGenes);
    }

    @Override
    public Individual incubate() {
        return new Warrior(species,genes);
    }

    @Override
    public Double getFitness() {
        if(fitness != null)return fitness;
        Double strenght=0d;
        Double agility=0d;
        Double wisdom=0d;
        Double resistance=0d;
        Double health=0d;

        for(Phenotype phenotype:genes.getAllPhenotypes()){
            strenght+=phenotype.getValue(BonusType.STRENGHT.toString());
            agility+=phenotype.getValue(BonusType.AGILITY.toString());
            wisdom+=phenotype.getValue(BonusType.WISDOM.toString());
            resistance+=phenotype.getValue(BonusType.RESISTANCE.toString());
            health+=phenotype.getValue(BonusType.HEALTH.toString());
        }

        strenght = 100 * Math.tanh(0.01 * strenght);
        agility = Math.tanh(0.01 * agility);
        wisdom = 0.6 * Math.tanh(0.01*wisdom);
        resistance = Math.tanh(0.01*resistance);
        health = 100 * Math.tanh(0.01 * health);

        Double height = genes.getPhenotypeByName("height").getValue("")/10.0;
        Double attackMod = 0.5-Math.pow(3*height-5,4)+Math.pow(3*height-5,2)+height/2;
        Double defenseMod = 2 + Math.pow(3*height-5,4)-Math.pow(3*height-5,2)-height/2;

        Double attack = (agility+wisdom)*strenght*attackMod;
        Double defense = (resistance+wisdom)*health*defenseMod;

        fitness = 0.6* attack + 0.4 * defense;
        return fitness;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[Warrior:");
        builder.append(getFitness().toString().substring(0,5));
        builder.append("]");
        return builder.toString();
    }
}
