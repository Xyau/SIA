package main;

import breeders.UniformBreeder;
import experiment.Experiment;
import experiment.ExperimentBuilder;
import individuals.Individual;
import javafx.util.Pair;
import mutators.SimpleMutator;
import selectors.*;
import utils.CSVWriter;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        CharacterFactory characterFactory = new CharacterFactory();
        List<Individual> startingPop = characterFactory.createRandomWarrior(2,random,5);


        System.out.println(startingPop.get(0));
        ExperimentBuilder builder = new ExperimentBuilder();
        builder.addSelector(new RouletteSquaredSelector(40,random))
                .addMutator(new SimpleMutator(0.8d,2d,random))
                .addBreeder(new UniformBreeder(new Random()))
                .addMaxGenerations(50)
                .addStartingPop(startingPop)
                .addWorkingPop(20)
                .addName("ex1").replacementNormal(0.5D,random);

        Experiment experiment = builder.buildExperiment();

        Map<String,List<Double>> timeseries = experiment.run();

        String out = CSVWriter.getTimeSeriesString(timeseries);
        System.out.println(out);
    }
}
