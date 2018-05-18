package main;

import breeders.SimpleCrossBreeder;
import experiment.Experiment;
import experiment.ExperimentBuilder;
import genes.Species;
import individuals.Individual;
import individuals.Warrior;
import javafx.util.Pair;
import mutators.SimpleMutator;
import selectors.*;
import utils.CSVWriter;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Species species = Warrior.generateSpecies();
        List<Individual> startingPop = Warrior.generateIndividuals(species,10,random);


        System.out.println(startingPop.get(0));
        ExperimentBuilder builder = new ExperimentBuilder();
        builder.addSelector(new EliteSelector(40))
                .addMutator(new SimpleMutator(0.8d,2d,random))
                .addBreeder(new SimpleCrossBreeder(new Random()))
                .addMaxGenerations(50)
                .addStartingPop(startingPop);

        Experiment experiment = builder.buildExperiment();

        List<Pair<String,List<Double>>> timeseries = experiment.run();

        String out = CSVWriter.getTimeSeriesString(timeseries);
        System.out.println(out);
    }
}
