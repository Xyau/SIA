package main;

import breeders.AnularBreeder;
import breeders.SimpleCrossBreeder;
import breeders.TwoPointCrossBreeder;
import breeders.UniformBreeder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import experiment.Experiment;
import experiment.ExperimentBuilder;
import individuals.Individual;
import interfaces.Breeder;
import interfaces.Mutator;
import interfaces.Selector;
import mutators.NoChangeMutator;
import mutators.SimpleMutator;
import selectors.*;
import utils.TSVReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
        Random random = new Random();
        ExperimentBuilder b = new ExperimentBuilder();

        setDataset(jsonObject);

        Breeder breeder = getBreeder(jsonObject,random);
        b.addBreeder(breeder);

        switch (jsonObject.get("mutator").getAsString().toLowerCase()){
            case "uniform":
                b.addMutator(new NoChangeMutator());
                break;
            case "notuniform":
                double mutationRatio = jsonObject.get("chanceToMutate").getAsDouble();
                double mutationStrenght = jsonObject.get("mutationStrenght").getAsDouble();
                b.addMutator(new SimpleMutator(mutationRatio, mutationStrenght, random));
                break;
            default:
                throw new IllegalStateException("No accepted mutator found");
        }

//        if (selectedIndividuals == null){
//            throw new IllegalStateException("No selected individuals option found");
//        }
        switch (jsonObject.get("selection").getAsString().toLowerCase()){
            case "elite":
                b.addSelector(new EliteSelector());
                break;
            case "roulette":
                b.addSelector(new RouletteSelector(random));
                break;
            case "squared":
                b.addSelector(new RouletteSquaredSelector(random));
                break;
            case "universal":
            case "boltzmann":
                b.addSelector(new BoltzmannSelector(random));
                break;
            case "tournament":
                Integer tourneySize = jsonObject.get("tourneySize").getAsInt();
                b.addSelector(new TournamentSelector(tourneySize, random));
                break;
            case "ranking":
                b.addSelector(new RankingSelector(random));
                break;
            case "hybrid" :
                b.addSelector(new HybridSelector(new RankingSelector(random),new EliteSelector(),0.7,0.3));
                break;
            default:
                throw new IllegalStateException("No accepted mutator found");
        }
        switch (jsonObject.get("replacement").getAsString().toLowerCase()){
            case "elite":
                b.addReplacement(new EliteSelector());
                break;
            case "roulette":
                b.addReplacement(new RouletteSelector(random));
                break;
            case "universal":
            case "boltzmann":
                break;
            case "tournament":
                Integer tourneySize = jsonObject.get("tourneySize").getAsInt();
                b.addReplacement(new TournamentSelector(tourneySize, random));
                break;
            case "ranking":
                break;
            default:
                throw new IllegalStateException("No accepted mutator found");
        }
        Integer parentAmount ;
        switch (jsonObject.get("experiment").getAsString().toLowerCase()){
            case "simple":
                b.replacementSimple();
                break;
            case "complex":
                parentAmount = jsonObject.get("parentAmount").getAsInt();
                b.replacementComplex( parentAmount,random);
                break;
            case "normal":
                parentAmount = jsonObject.get("parentAmount").getAsInt();
                b.replacementNormal(parentAmount, random);
                break;
            default:
                throw new IllegalStateException("No accepted replacement method found");
        }


        Integer maxGenerations = jsonObject.get("maxGenerations").getAsInt();
        b.addMaxGenerations(maxGenerations);

        List<Individual> startingPop = getPopulation(jsonObject,random);
        System.out.println(startingPop.get(0));
        b.addStartingPop(startingPop);
        
        String name = jsonObject.get("name").getAsString();
        b.addName(name);
        return b.buildExperiment();
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
            case "assasin":
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
