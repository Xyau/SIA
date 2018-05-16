package main;

import breeders.AvergageBreeder;
import experiment.Experiment;
import experiment.ExperimentBuilder;
import individuals.BitsetIndividual;
import individuals.Individual;
import mutators.SimpleMutator;
import selectors.TopNSelector;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        List<Individual> startingPop = new ArrayList<>();
        startingPop.add(new BitsetIndividual(random));
        startingPop.add(new BitsetIndividual(random));

        System.out.println(startingPop.get(0));
        ExperimentBuilder builder = new ExperimentBuilder();
        builder.addSelector(new TopNSelector(1))
                .addMutator(new SimpleMutator(0.2d,2d,random))
                .addBreeder(new AvergageBreeder(new Random()))
                .addMaxGenerations(100)
                .addStartingPop(startingPop);

        Experiment experiment = builder.buildExperiment();

        experiment.run();

    }
}
