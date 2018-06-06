package main;

import breeders.AnularBreeder;
import breeders.SimpleCrossBreeder;
import breeders.TwoPointCrossBreeder;
import breeders.UniformBreeder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import experiment.Experiment;
import experiment.ExperimentBuilder;
import individuals.Individual;
import interfaces.Breeder;
import interfaces.Mutator;
import interfaces.Selector;
import mutators.EvolvingMutator;
import mutators.SimpleMutator;
import selectors.*;
import utils.TSVReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Configuration {

    /*
     * {
     *      "breeder": ...,
     *      "mutation": "uniform"/"notuniform",
     *      "selection": ...,
     *      "replacement": ...,
     *      "halt": ...
     * }
     */
    public static Experiment getExperiment(String pathname) throws IOException {
        File file = new File(pathname);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        String str = new String(data, "UTF-8");
        JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();

        ExperimentBuilder b = new ExperimentBuilder();

        Random random = getRandom(jsonObject);
        setDataset(jsonObject);

        Breeder breeder = getBreeder(jsonObject,random);
        b.addBreeder(breeder);

        Mutator mutator = getMutator(jsonObject,random);
        b.addMutator(mutator);

        Selector selector = getSelector(jsonObject,"selector",random);
        b.addSelector(selector);

        Selector replacement = getSelector(jsonObject,"replacement",random);
        b.addReplacement(replacement);

        setCutConditions(jsonObject,b);

        setExperiment(jsonObject,random,b);

        List<Individual> startingPop = getPopulation(jsonObject,random);
        System.out.println(startingPop.get(0));
        b.addStartingPop(startingPop);
        
        String name = jsonObject.get("name").getAsString();
        b.addName(name);
        return b.buildExperiment();
    }

    private static Random getRandom(JsonObject jsonObject) {
        JsonElement jsonElement = jsonObject.get("seed");
        if(jsonElement != null){
            return new Random(jsonElement.getAsInt());
        }
        else return new Random();
    }

    private static void setExperiment(JsonObject jsonObject, Random random, ExperimentBuilder experimentBuilder){
        JsonElement jsonElement = jsonObject.get("experiment");
        Double parentRatio;
        switch (jsonElement.getAsString().toLowerCase()){
            case "simple":
                experimentBuilder.replacementSimple();
                return;
            case "complex":
                parentRatio = jsonObject.get("parentRatio").getAsDouble();
                experimentBuilder.replacementComplex(parentRatio, random);
                return;
            case "normal":
                parentRatio = jsonObject.get("parentRatio").getAsDouble();
                experimentBuilder.replacementNormal(parentRatio, random);
                return;
        }
        throw new IllegalStateException("No accepted experiment method found");
    }

    private static Mutator getMutator(JsonObject jsonObject, Random random){
        JsonElement jsonElement = jsonObject.get("mutator");
        switch (jsonElement.getAsString().toLowerCase()){
            case "evolving":
                double startRatio = jsonObject.get("startRatio").getAsDouble();
                double endRatio = jsonObject.get("endRatio").getAsDouble();
                int duration = jsonObject.get("duration").getAsInt();
                return new EvolvingMutator(startRatio,endRatio,duration,random);
            case "uniform":
                double mutationRatio = jsonObject.get("chanceToMutate").getAsDouble();
                double mutationStrenght = jsonObject.get("mutationStrength").getAsDouble();
                return new SimpleMutator(mutationRatio, mutationStrenght, random);
        }
        throw new IllegalStateException("No accepted mutator found");
    }

    private static Selector getSelector(JsonObject jsonObject, String selectorName, Random random){
        JsonElement jsonElement = jsonObject.get(selectorName);
        if(jsonElement.isJsonObject()){
            JsonObject nestedObject = jsonElement.getAsJsonObject();

            Selector first = getSelector(nestedObject,"first",random);
            Selector second = getSelector(nestedObject,"second",random);
            Double firstToTotalRatio = nestedObject.get("firstToTotalRatio").getAsDouble();
            return new HybridSelector(first,second,firstToTotalRatio);
        } else {
            if(jsonElement != null) {
                switch (jsonElement.getAsString().toLowerCase()) {
                    case "elite":
                        return new EliteSelector();
                    case "roulette":
                        return new RouletteSelector(random);
                    case "squared":
                        return new RouletteSquaredSelector(random);
                    case "universal":
                    case "boltzmann":
                        return new BoltzmannSelector(random);
                    case "tournament":
                        Integer tourneySize = jsonObject.get("tourneySize").getAsInt();
                        return new TournamentSelector(tourneySize, random);
                    case "randomtournament":
                        return new RandomTournamentSelector(random);
                    case "ranking":
                        return new RankingSelector(random);
                    case "random":
                        return new RandomSelector(random);
                }
            }
        }
        throw new IllegalStateException("No accepted "+ selectorName + " found");

    }

    private static void setCutConditions(JsonObject jsonObject, ExperimentBuilder experimentBuilder){
        JsonElement element = jsonObject.get("targetFitness");
        if(element != null){
            Double targetFitness = element.getAsDouble();
            experimentBuilder.addTargetFitness(targetFitness);
        }

        element = jsonObject.get("maxStaleBestFitnessGenerations");
        if(element != null) {
            Integer maxStaleGenerations = element.getAsInt();
            experimentBuilder.addmaxStaleBestFitnessGenerations(maxStaleGenerations);
        }

        element = jsonObject.get("maxStalePopulation");
        if(element != null){
            Integer maxStalePopulation = element.getAsInt();
            experimentBuilder.addMaxStaleIndividuals(maxStalePopulation);
        }

        element = jsonObject.get("maxGenerations");
        if(element != null){
            Integer maxGenerations = element.getAsInt();
            experimentBuilder.addMaxGenerations(maxGenerations);
        }
    }

    private static void setDataset(JsonObject jsonObject){
        String dataset = jsonObject.get("dataset").getAsString();
        switch (dataset){
            case "full":
                TSVReader.fullData = true;
                break;
            case "test":
            default:
                TSVReader.fullData = false;
        }
    }

    private static List<Individual> getPopulation(JsonObject jsonObject, Random random){
        CharacterFactory characterFactory = new CharacterFactory();
        List<Individual> pop ;
        Integer variant = jsonObject.get("variant").getAsInt();
        if(variant > 3 || variant < 1){
            throw new IllegalStateException("variant must be between 0 and 2");
        }
        Integer amount = jsonObject.get("amount").getAsInt();
        if(variant < 2){
            throw new IllegalStateException("Must have greater than 1 individuals");
        }
        switch (jsonObject.get("type").getAsString().toLowerCase()){
            case "warrior":
                pop = characterFactory.createRandomWarrior(variant,random,amount);
                break;
            case "archer":
                pop = characterFactory.createRandomArcher(variant,random,amount);
                break;
            case "defender":
                pop = characterFactory.createRandomDefender(variant,random,amount);
                break;
            case "assassin":
                pop = characterFactory.createRandomAssasin(variant,random,amount);
                break;
            default:
                throw new IllegalStateException("No accepted individual type");
        }
        return pop;
    }

    private static Breeder getBreeder(JsonObject jsonObject, Random random){
        Float chanceToBreed = jsonObject.get("chanceToBreed").getAsFloat();
        Breeder breeder;
        switch (jsonObject.get("breeder").getAsString().toLowerCase()){
            case "anular":
                breeder = new AnularBreeder(random,chanceToBreed);
                break;
            case "simple":
                breeder = new SimpleCrossBreeder(random,chanceToBreed);
                break;
            case "twopoint":
                breeder = new TwoPointCrossBreeder(random,chanceToBreed);
                break;
            case "uniform":
                breeder = new UniformBreeder(random,chanceToBreed);
                break;
            default:
                throw new IllegalStateException("No accepted breeder found");
        }
        return breeder;
    }

}
