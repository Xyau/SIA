package main;

import breeders.SimpleCrossBreeder;
import experiment.Experiment;
import experiment.ExperimentBuilder;
import individuals.BitsetIndividual;
import individuals.Individual;
import mutators.NoChangeMutator;
import mutators.SimpleBitMutator;
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
        builder.addSelector(new TopNSelector(10))
                .addMutator(new SimpleBitMutator(1d,random))
                .addBreeder(new SimpleCrossBreeder(new Random()))
                .addMaxGenerations(100)
                .addStartingPop(startingPop);

        Experiment experiment = builder.buildExperiment();

        experiment.run();

    }
}
