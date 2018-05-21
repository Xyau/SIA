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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

public class Character extends Individual {
    final Double fitness;

    final Double strenghtBonus;
    final Double agilityBonus;
    final Double wisdomBonus;
    final Double resistanceBonus;
    final Double healthBonus;

    final String name;

    final BiFunction<Double,Double,Double> finalFitness;

    public Character(Species species, Random random,String name, Double strenghtBonus, Double agilityBonus,
                     Double wisdomBonus, Double resistanceBonus, Double healthBonus,
                     BiFunction<Double, Double, Double> finalFitness) {
        this(species,species.getRandomGenes(random),name,strenghtBonus,agilityBonus,wisdomBonus,resistanceBonus,healthBonus,finalFitness);
    }

    public Character(Species species, Genes genes, String name, Double strenghtBonus, Double agilityBonus,
                     Double wisdomBonus, Double resistanceBonus, Double healthBonus,
                     BiFunction<Double, Double, Double> finalFitness) {
        this.genes = genes;
        this.species = species;
        this.strenghtBonus = strenghtBonus;
        this.agilityBonus = agilityBonus;
        this.wisdomBonus = wisdomBonus;
        this.resistanceBonus = resistanceBonus;
        this.healthBonus = healthBonus;
        this.name = name;
        this.finalFitness = finalFitness;
        fitness = getFitness();
    }

    public static Species generateSpecies(){
        List<Genotype> genotypes = new ArrayList<>();
        genotypes.add(new ItemGenotype(ItemType.HELMET,TSVReader.parseFile(ItemType.HELMET)));
        genotypes.add(new ItemGenotype(ItemType.ARMOR,TSVReader.parseFile(ItemType.ARMOR)));
        genotypes.add(new ItemGenotype(ItemType.GLOVES,TSVReader.parseFile(ItemType.GLOVES)));
        genotypes.add(new ItemGenotype(ItemType.BOOTS,TSVReader.parseFile(ItemType.BOOTS)));
        genotypes.add(new ItemGenotype(ItemType.WEAPON,TSVReader.parseFile(ItemType.WEAPON)));
        genotypes.add(new IntegerGenotype(13,20,"height"));
        Species species = new Species("Character",genotypes);
        return species;
    }

    @Override
    public Individual incubate(Genes alteredGenes) {
        return new Character(species,alteredGenes,name,strenghtBonus,agilityBonus,wisdomBonus,resistanceBonus,healthBonus,finalFitness);
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

        strenght = 100 * Math.tanh(0.01 * strenght * strenghtBonus);
        agility = Math.tanh(0.01 * agility * agilityBonus);
        wisdom = 0.6 * Math.tanh(0.01 * wisdom * wisdomBonus);
        resistance = Math.tanh(0.01 * resistance * resistanceBonus);
        health = 100 * Math.tanh(0.01 * health * healthBonus);

        Double height = genes.getPhenotypeByName("height").getValue("")/10.0;
        Double attackMod = 0.5-Math.pow(3*height-5,4)+Math.pow(3*height-5,2)+height/2;
        Double defenseMod = 2 + Math.pow(3*height-5,4)-Math.pow(3*height-5,2)-height/2;

        Double attack = (agility+wisdom)*strenght*attackMod;
        Double defense = (resistance+wisdom)*health*defenseMod;

        return finalFitness.apply(attack,defense);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[").append(name).append(":");
        builder.append(getFitness().toString(), 0, 5);
        builder.append("]");
        return builder.toString();
    }
}
