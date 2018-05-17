package selectors;

import individuals.Individual;
import interfaces.Selector;

import java.util.*;
import java.util.stream.Collectors;

public class RandomBiasedSelector extends BaseSelector implements Selector {
    private Random random;
    public RandomBiasedSelector(Integer selectedIndividuals, Random random) {
        super(selectedIndividuals);
        this.random = random;
    }

    @Override
    public List<Individual> selectChampions(List<Individual> candidates) {
        List<Individual> champions = new ArrayList<>();

        Double totalFitness = candidates.stream().collect(Collectors.summingDouble(ind-> ind.getFitness()));
        Double accumulatedFitness=0D;
        NavigableMap<Double,Individual> map = new TreeMap<>();
        for (Individual individual:candidates){
            map.put((accumulatedFitness)/totalFitness,individual);
            accumulatedFitness += individual.getFitness();
        }

        for (int i = 0; i < selectedIndividuals && i < candidates.size(); i++) {
            champions.add(map.floorEntry(random.nextDouble()).getValue());
        }
        return champions;
    }
}
