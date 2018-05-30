package selectors;

import individuals.Individual;
import interfaces.Selector;

import java.util.*;

public class RankingSelector implements Selector {
    private Random random;
    public RankingSelector(Random random) {
        this.random = random;
    }

    @Override
    public List<Individual> selectChampions(List<Individual> candidates, Integer amount, Integer generation) {

        if (candidates.size() <= 1) {
            return candidates;
        }

        ArrayList<Individual> sortedCandidates = new ArrayList<Individual>(candidates);
        Collections.sort(sortedCandidates, (i1,i2)-> i1.getFitness() > i2.getFitness() ? 1 : -1);

        List<Individual> champions = new ArrayList<>();

        Double totalFitness = sortedCandidates.size() * (sortedCandidates.size()+1)/2d;
        Double accumulatedFitness = 0D;
        NavigableMap<Double, Individual> map = new TreeMap<>();
        int idx  = 1;
        for (Individual individual : sortedCandidates) {
            accumulatedFitness += idx++;
            map.put((accumulatedFitness) / totalFitness, individual);
        }

        for (int i = 0; i < Math.min(amount, sortedCandidates.size()); i++) {
            champions.add(map.ceilingEntry(random.nextDouble()).getValue());
        }
        return champions;
    }
}
