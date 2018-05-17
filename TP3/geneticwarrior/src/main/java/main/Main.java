package main;

import breeders.SimpleCrossBreeder;
import experiment.Experiment;
import experiment.ExperimentBuilder;
import genes.Species;
import individuals.Individual;
import individuals.Warrior;
import javafx.util.Pair;
import mutators.SimpleMutator;
import selectors.EliteSelector;
import selectors.RandomBiasedSelector;
import selectors.RandomBiasedSquaredSelector;
import selectors.RandomSelector;
import utils.CSVWriter;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        List<Individual> startingPop = new ArrayList<>();
        Species species = Warrior.generateSpecies();
        startingPop.add(new Warrior(species,random));
        startingPop.add(new Warrior(species,random));
        startingPop.add(new Warrior(species,random));
        startingPop.add(new Warrior(species,random));
        startingPop.add(new Warrior(species,random));
        startingPop.add(new Warrior(species,random));

        System.out.println(startingPop.get(0));
        ExperimentBuilder builder = new ExperimentBuilder();
        builder.addSelector(new EliteSelector(20))
                .addMutator(new SimpleMutator(0.0d,2d,random))
                .addBreeder(new SimpleCrossBreeder(new Random()))
                .addMaxGenerations(400)
                .addStartingPop(startingPop);

        Experiment experiment = builder.buildExperiment();

        List<Pair<String,List<Double>>> timeseries = experiment.run();

        String out = CSVWriter.getTimeSeriesString(timeseries);
        System.out.println(out);
    }
}
