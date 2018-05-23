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
import selectors.EliteSelector;
import selectors.RouletteSelector;
import selectors.TournamentSelector;

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
        Random random = new Random();
        ExperimentBuilder b = new ExperimentBuilder();

        switch (jsonObject.get("breeder").getAsString().toLowerCase()){
            case "anular":
                b.addBreeder(new AnularBreeder(random));
                break;
            case "simple":
                b.addBreeder(new SimpleCrossBreeder(random));
                break;
            case "twopoint":
                b.addBreeder(new TwoPointCrossBreeder(random));
                break;
            case "uniform":
                b.addBreeder(new UniformBreeder(random));
                break;
            default:
                throw new IllegalStateException("No accepted breeder found");
        }

        switch (jsonObject.get("mutator").getAsString().toLowerCase()){
            case "uniform":
                b.addMutator(new NoChangeMutator());
                break;
            case "notuniform":
                double mutationRatio = jsonObject.get("mutationRatio").getAsDouble();
                double mutationStrenght = jsonObject.get("mutationStrenght").getAsDouble();
                b.addMutator(new SimpleMutator(mutationRatio, mutationStrenght, random));
                break;
            default:
                throw new IllegalStateException("No accepted mutator found");
        }

        Integer selectedIndividuals = jsonObject.get("selectedIndividuals").getAsInt();
//        if (selectedIndividuals == null){
//            throw new IllegalStateException("No selected individuals option found");
//        }
        switch (jsonObject.get("selection").getAsString().toLowerCase()){
            case "elite":
                b.addSelector(new EliteSelector(selectedIndividuals));
                break;
            case "roulette":
                b.addSelector(new RouletteSelector(selectedIndividuals, random));
                break;
            case "universal":
            case "boltzmann":
                break;
            case "tournament":
                Integer tourneySize = jsonObject.get("tourneySize").getAsInt();
                b.addSelector(new TournamentSelector(selectedIndividuals, tourneySize, random));
                break;
            case "ranking":
                break;
            default:
                throw new IllegalStateException("No accepted mutator found");
        }

        switch (jsonObject.get("replacement").getAsString().toLowerCase()){
            case "simple":
                b.replacementSimple();
                break;
            case "complex":
                Double untouchedRatio = jsonObject.get("untouchedRatio").getAsDouble();
                b.replacementComplex(untouchedRatio, random);
                break;
            case "normal":
                untouchedRatio = jsonObject.get("untouchedRatio").getAsDouble();
                b.replacementNormal(untouchedRatio, random);
                break;
            default:
                throw new IllegalStateException("No accepted replacement method found");
        }

        Integer maxGenerations = jsonObject.get("maxGenerations").getAsInt();
        b.addMaxGenerations(maxGenerations);
        Integer workingPop = jsonObject.get("workingPop").getAsInt();
        b.addWorkingPop(workingPop);

        CharacterFactory characterFactory = new CharacterFactory();
        List<Individual> startingPop = characterFactory.createRandomWarrior(2,random,5);
        System.out.println(startingPop.get(0));
        b.addStartingPop(startingPop);
        
        String name = jsonObject.get("name").getAsString();
        b.addName(name);
        return b.buildExperiment();
    }

}
