package breeders;

import experiment.Experiment;
import experiment.ExperimentBuilder;
import individuals.Individual;
import javafx.util.Pair;
import main.CharacterFactory;
import mutators.EvolvingMutator;
import org.apache.log4j.*;
import org.junit.jupiter.api.Test;
import selectors.*;
import utils.CSVWriter;
import utils.TSVReader;

import java.util.*;
import java.util.stream.Collectors;

public class MarathonTest {
    @Test
    void marathonTest(){
        Appender appender = new ConsoleAppender(new SimpleLayout());
        appender.setName("root");
        Logger.getRootLogger().addAppender(appender);
        Logger.getRootLogger().setLevel(Level.INFO);

        Integer suiteSize = 300;
        ExperimentBuilder builder = new ExperimentBuilder();
        Random charactersRandom = new Random(3);
        Random random = new Random(123);
        TSVReader.fullData=false;
        Map<String,List<Double>> timeseries = new HashMap<>();
        CharacterFactory characterFactory = new CharacterFactory(random);
        Integer amount = 50;
        List<Individual> starting = characterFactory.createRandomWarrior(2,random,amount);
        String name = "TournamentTournamentFull";

        builder.addMutator(new EvolvingMutator(1d,0.8,400,random))
                .addBreeder(new SimpleCrossBreeder(random,0.9f))
//                .addReplacement(new HybridSelector(new EliteSelector(),new RouletteSelector(random),0.3))
//                .addSelector(new HybridSelector(new EliteSelector(),new RouletteSelector(random),0.3))
                .addReplacement(new TournamentSelector(5,random))
                .addSelector(new TournamentSelector(5,random))
                .addMaxGenerations(1500)
                .replacementNormal(0.6,random)
                .addName(name)
                .addTargetFitness(48d)
                .addStartingPop(starting);

        List<Experiment> experiments = new ArrayList<>();

        for (int i = 0; i < suiteSize; i++) {
            builder.addStartingPop(characterFactory.createRandomWarrior(2,charactersRandom,amount));
            builder.addName(name+" "+i);
            experiments.add(builder.buildExperiment());
            Logger.getRootLogger().info("loading "+i);
        }

        List<Pair<Double,Double>> finalMax =experiments.parallelStream().map(Experiment::run).flatMap(x->x.entrySet().stream())
                .filter(x->x.getKey().contains("max"))
                .map(x->{
                    Integer size = x.getValue().size();
                    return new Pair<>(size.doubleValue() - 1, x.getValue().get(size - 1));
                }).collect(Collectors.toList());
        Collections.sort(finalMax,Comparator.comparing(Pair::getKey));

        timeseries.put(name+" gen",finalMax.stream().map(Pair::getKey).collect(Collectors.toList()));
        timeseries.put(name+" max",finalMax.stream().map(Pair::getValue).collect(Collectors.toList()));

        List<Map.Entry<Integer, Long>> hist = new ArrayList<>(finalMax.stream().map(Pair::getKey).collect(Collectors
                .groupingBy(x -> x.intValue() - x.intValue() % 200, HashMap::new, Collectors.counting())).entrySet());

        Collections.sort(hist,Comparator.comparing(Map.Entry::getKey));

        timeseries.put(name+ " bins",hist.stream().map(x->x.getKey().doubleValue()).collect(Collectors.toList()));
        timeseries.put(name+ " amount",hist.stream().map(x->x.getValue().doubleValue()).collect(Collectors.toList()));

        String out = CSVWriter.getTimeSeriesString(timeseries);
        CSVWriter.writeOutput(name+"Analisis.csv",out);
    }
}
