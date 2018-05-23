package main;

import breeders.UniformBreeder;
import experiment.Experiment;
import experiment.ExperimentBuilder;
import individuals.Individual;
import javafx.util.Pair;
import mutators.SimpleMutator;
import selectors.*;
import utils.CSVWriter;
import utils.TSVReader;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        CharacterFactory characterFactory = new CharacterFactory();
        List<Individual> startingPop = characterFactory.createRandomWarrior(2,random,200);


        System.out.println(startingPop.get(0));
        ExperimentBuilder builder = new ExperimentBuilder();
        builder.addSelector(new EliteSelector())
                .addReplacement(new EliteSelector())
                .addMutator(new SimpleMutator(0.8d,2d,random))
                .addBreeder(new UniformBreeder(random,0.9f))
                .addMaxGenerations(50)
                .addStartingPop(startingPop)
                .addName("ex1").replacementComplex(4,random);

        Experiment experiment = builder.buildExperiment();

        Map<String,List<Double>> timeseries = experiment.run();

        String out = CSVWriter.getTimeSeriesString(timeseries);
        System.out.println(out);
    }
}
