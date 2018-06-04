package breeders;

import experiment.Experiment;
import experiment.ExperimentBuilder;
import individuals.Individual;
import javafx.css.CssMetaData;
import main.CharacterFactory;
import mutators.EvolvingMutator;
import mutators.SimpleMutator;
import org.apache.log4j.*;
import org.junit.jupiter.api.Test;
import selectors.*;
import sun.rmi.runtime.Log;
import utils.CSVWriter;
import utils.TSVReader;

import java.util.*;

public class Experiments {
    @Test
    public void testSelectors(){
        Appender appender = new ConsoleAppender(new SimpleLayout());
        appender.setName("root");
        Logger.getRootLogger().addAppender(appender);
        Logger.getRootLogger().setLevel(Level.INFO);

        ExperimentBuilder builder = new ExperimentBuilder();
        Random random = new Random(124);
        TSVReader.fullData=true;
        Map<String,List<Double>> timeseries = new HashMap<>();
        CharacterFactory characterFactory = new CharacterFactory();
        List<Individual> starting = characterFactory.createRandomWarrior(2,random,30);

        builder.addMutator(new SimpleMutator(0.5,1d,random))
                .addBreeder(new AnularBreeder(random,0.9f))
                .addReplacement(new EliteSelector())
                .addSelector(new EliteSelector())
                .addMaxGenerations(150)
                .replacementNormal(0.7,random)
                .addName("Elite")
                .addTargetFitness(47d)
                .addStartingPop(starting);

        List<Experiment> experiments = new ArrayList<>();
        experiments.add(builder.buildExperiment());

        builder.addName("Roulette").addSelector(new RouletteSelector(random));
        experiments.add(builder.buildExperiment());

        builder.addName("Squared").addSelector(new RouletteSquaredSelector(random));
        experiments.add(builder.buildExperiment());

        builder.addName("10-Tourney").addSelector(new TournamentSelector(10,random));
        experiments.add(builder.buildExperiment());

        builder.addName("RandomTourney").addSelector(new RandomTournamentSelector(random));
        experiments.add(builder.buildExperiment());

        builder.addName("Random").addSelector(new RandomSelector(random));
        experiments.add(builder.buildExperiment());

        experiments.parallelStream().map(Experiment::run).forEach(timeseries::putAll);

        String out = CSVWriter.getTimeSeriesString(timeseries);
        CSVWriter.writeOutput("selectorComparisons.csv",out);
    }

    @Test
    public void testExperiments(){
        Appender appender = new ConsoleAppender(new SimpleLayout());
        appender.setName("root");
        Logger.getRootLogger().addAppender(appender);
        Logger.getRootLogger().setLevel(Level.INFO);

        ExperimentBuilder builder = new ExperimentBuilder();
        Random random = new Random(221);
        TSVReader.fullData=true;
        Map<String,List<Double>> timeseries = new HashMap<>();
        CharacterFactory characterFactory = new CharacterFactory();
        List<Individual> starting = characterFactory.createRandomWarrior(2,random,50);

        builder.addMutator(new EvolvingMutator(1d,0.2,15000,random))
                .addBreeder(new SimpleCrossBreeder(random,0.9f))
                .addReplacement(new HybridSelector(new EliteSelector(),new RouletteSelector(random),0.3))
                .addSelector(new HybridSelector(new EliteSelector(),new RouletteSelector(random),0.3))
                .addMaxGenerations(20000)
                .replacementNormal(0.4,random)
                .addName("Normal")
                .addStartingPop(starting);

        List<Experiment> experiments = new ArrayList<>();
        experiments.add(builder.buildExperiment());

        builder.addName("Simple").replacementSimple();
        experiments.add(builder.buildExperiment());

        builder.addName("Complex").replacementComplex(0.4,random);
        experiments.add(builder.buildExperiment());

        experiments.parallelStream().map(Experiment::run).forEach(timeseries::putAll);

        String out = CSVWriter.getTimeSeriesString(timeseries);
        CSVWriter.writeOutput("experimentComparisons3.csv",out);
    }

    @Test
    public void testBreeders(){
        Appender appender = new ConsoleAppender(new SimpleLayout());
        appender.setName("root");
        Logger.getRootLogger().addAppender(appender);
        Logger.getRootLogger().setLevel(Level.INFO);

        ExperimentBuilder builder = new ExperimentBuilder();
        Random random = new Random(21);
        TSVReader.amount=10000d;
        TSVReader.fullData=true;
        Map<String,List<Double>> timeseries = new HashMap<>();
        CharacterFactory characterFactory = new CharacterFactory();
        List<Individual> starting = characterFactory.createRandomWarrior(2,random,20);

        builder.addMutator(new EvolvingMutator(1d,0.8,400,random))
                .addBreeder(new SimpleCrossBreeder(random,0.9f))
                .addReplacement(new HybridSelector(new EliteSelector(),new RouletteSelector(random),0.3))
                .addSelector(new HybridSelector(new EliteSelector(),new RouletteSelector(random),0.3))
                .addMaxGenerations(20000)
                .replacementComplex(1.0,random)
                .addName("SimpleCorss")
                .addStartingPop(starting);

        List<Experiment> experiments = new ArrayList<>();
        experiments.add(builder.buildExperiment());

        builder.addName("DoubleCross").addBreeder(new TwoPointCrossBreeder(random,0.9f));
        experiments.add(builder.buildExperiment());

        builder.addName("Anular").addBreeder(new AnularBreeder(random,0.9f));
        experiments.add(builder.buildExperiment());

        builder.addName("Uniform").addBreeder(new UniformBreeder(random,0.9f));
        experiments.add(builder.buildExperiment());

        experiments.parallelStream().map(Experiment::run).forEach(timeseries::putAll);

        String out = CSVWriter.getTimeSeriesString(timeseries);
        CSVWriter.writeOutput("breederComparisons.csv",out);
    }
}

