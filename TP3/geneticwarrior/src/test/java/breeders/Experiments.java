package breeders;

import experiment.Experiment;
import experiment.ExperimentBuilder;
import individuals.Individual;
import javafx.css.CssMetaData;
import main.CharacterFactory;
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
                .replacementNormal(20,random)
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
    }@Test
    public void testExperiments(){
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
                .replacementNormal(20,random)
                .addName("Normal")
                .addTargetFitness(47d)
                .addStartingPop(starting);

        List<Experiment> experiments = new ArrayList<>();
        experiments.add(builder.buildExperiment());

        builder.addName("Simple").replacementSimple();
        experiments.add(builder.buildExperiment());

        builder.addName("Complex").replacementComplex(20,random);
        experiments.add(builder.buildExperiment());

        experiments.parallelStream().map(Experiment::run).forEach(timeseries::putAll);

        String out = CSVWriter.getTimeSeriesString(timeseries);
        CSVWriter.writeOutput("experimentComparisons.csv",out);
    }
}

