package selectors;

import individuals.Individual;
import interfaces.Selector;

import java.util.*;
import java.util.stream.Collectors;

public class RouletteSquaredSelector extends BaseSelector implements Selector {
    private Random random;
    public RouletteSquaredSelector(Integer selectedIndividuals, Random random) {
        super(selectedIndividuals);
        this.random = random;
    }

    @Override
    public List<Individual> selectChampions(List<Individual> candidates) {
        List<Individual> champions = new ArrayList<>();

        Double totalFitness = candidates.stream().map(x -> Math.pow(x.getFitness(), 2d))
                .mapToDouble(x -> x).sum();
        Double accumulatedFitness=0D;
        NavigableMap<Double,Individual> map = new TreeMap<>();
        for (Individual individual:candidates){
            accumulatedFitness += Math.pow(individual.getFitness(),2);
            map.put((accumulatedFitness)/totalFitness,individual);
        }

        for (int i = 0; i < selectedIndividuals && i < candidates.size(); i++) {
            champions.add(map.ceilingEntry(random.nextDouble()).getValue());
        }
        return champions;
    }
}
