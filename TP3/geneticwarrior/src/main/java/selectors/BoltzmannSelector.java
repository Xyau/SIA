package selectors;

import individuals.Individual;
import interfaces.Selector;

import java.util.*;

public class BoltzmannSelector implements Selector {
    private Random random;
    private Double initTemp;
    public BoltzmannSelector(Random random,Double initTemp) {
        this.random = random;
        this.initTemp = initTemp;
    }

    @Override
    public List<Individual> selectChampions(List<Individual> candidates, Integer amount, Integer generation) {
        Double temperature = Math.max(0.1,initTemp * (1 - (1 / (1 + Math.exp(-generation)))));
        if(candidates.size() <= 1) {
            return candidates;
        }
        List<Individual> champions = new ArrayList<>();

        Double totalFitness = (double)candidates.size();
        Double aux = candidates.stream().mapToDouble(ind -> Math.exp(ind.getFitness()/temperature)).sum() / candidates.size();
        Double accumulatedFitness=0D;
        NavigableMap<Double,Individual> map = new TreeMap<>();
        for (Individual individual:candidates){
            accumulatedFitness += Math.exp(individual.getFitness()/temperature) / aux;
            map.put((accumulatedFitness)/totalFitness,individual);
        }

        for (int i = 0; i < Math.min(amount,candidates.size()); i++) {
            champions.add(map.ceilingEntry(random.nextDouble()).getValue());
        }
        return champions;
    }
}
