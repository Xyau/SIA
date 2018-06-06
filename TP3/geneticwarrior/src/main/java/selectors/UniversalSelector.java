package selectors;

import individuals.Individual;
import interfaces.Selector;

import java.util.*;

public class UniversalSelector extends BaseSelector implements Selector {
    private Random random;
    public UniversalSelector(Random random) {
        this.random = random;
    }

    @Override
    public List<Individual> selectChampions(List<Individual> candidates, Integer amount, Integer generation) {
        if(candidates.size() <= 1) {
            return candidates;
        }
        List<Individual> champions = new ArrayList<>();

        Double totalFitness = candidates.stream().mapToDouble(ind -> ind.getFitness()).sum();
        Double accumulatedFitness=0D;
        NavigableMap<Double,Individual> map = new TreeMap<>();
        for (Individual individual:candidates){
            accumulatedFitness += individual.getFitness();
            map.put((accumulatedFitness)/totalFitness,individual);
        }
        Double rnd = random.nextDouble();
        for (int i = 0; i < Math.min(amount,candidates.size()); i++) {
            champions.add(map.ceilingEntry((rnd+i-1)/Math.min(amount,candidates.size())).getValue());
        }
        return champions;
    }

}
