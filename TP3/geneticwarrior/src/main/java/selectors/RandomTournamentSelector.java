package selectors;

import individuals.Individual;
import interfaces.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTournamentSelector extends BaseSelector implements Selector {
    private Random random;
    public RandomTournamentSelector(Random random) {
        this.random = random;
    }

    @Override
    public List<Individual> selectChampions(List<Individual> candidates, Integer amount, Integer generation) {
        if(candidates.size() <= 1) {
            return candidates;
        }
        List<Individual> champions = new ArrayList<>();
        Individual best;
        Individual contender;

        for (int i = 0; i < Math.min(amount,candidates.size()); i++) {
            best = candidates.get(random.nextInt(candidates.size()-1));
            contender = candidates.get(random.nextInt(candidates.size()-1));
            if(best.getFitness()>contender.getFitness()){
                champions.add(random.nextDouble()>0.25?best:contender);
            } else {
                champions.add(random.nextDouble()>0.25?contender:best);
            }
        }
        return champions;
    }
}
