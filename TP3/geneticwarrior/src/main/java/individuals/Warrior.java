package individuals;

import genes.Genes;
import genes.IntegerGenotype;
import genes.ItemGenotype;
import genes.Species;
import interfaces.Genotype;
import main.ItemType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Warrior extends Individual {

    private Species generateSpecies(){
        List<Genotype> genotypes = new ArrayList<>();
        genotypes.add(new ItemGenotype(ItemType.HELMET,));
        genotypes.add(new IntegerGenotype(0,10,"A"));
        Species species = new Species("BitSet",genotypes);
        return species;
    }
    @Override
    public Individual incubate(Genes alteredGenes) {
        return null;
    }

    @Override
    public Individual incubate() {
        return null;
    }

    @Override
    public Number getFitness() {
        return null;
    }


}
