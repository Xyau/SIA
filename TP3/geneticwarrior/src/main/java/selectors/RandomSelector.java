package selectors;

import individuals.Individual;
import interfaces.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSelector extends BaseSelector implements Selector {
    private Random random;
    public RandomSelector(Integer selectedIndividuals,Random random) {
        super(selectedIndividuals);
        this.random = random;
    }

    @Override
    public List<Individual> selectChampions(List<Individual> candidates) {
        List<Individual> champions = new ArrayList<>();
        for (int i = 0; i < selectedIndividuals; i++) {
            champions.add(candidates.get(random.nextInt(candidates.size()-1)));
        }
        return champions;
    }
}
