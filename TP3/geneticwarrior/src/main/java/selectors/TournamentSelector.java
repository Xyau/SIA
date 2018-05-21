package selectors;

import individuals.Individual;
import interfaces.Selector;

import java.util.*;
import java.util.stream.Collectors;

public class TournamentSelector extends BaseSelector implements Selector {
    private Random random;
    private Integer tourneySize;

    public TournamentSelector(Integer selectedIndividuals, Integer tourneySize, Random random) {
        super(selectedIndividuals);
        this.tourneySize = tourneySize;
        this.random = random;
    }

    @Override
    public List<Individual> selectChampions(List<Individual> candidates, Integer generation) {
        return selectChampions(candidates, selectedIndividuals, generation);
    }

    @Override
    public List<Individual> selectChampions(List<Individual> candidates, Integer amount, Integer generation) {
        if (candidates.size() <= 1) {
            return candidates;
        }
        List<Individual> champions = new ArrayList<>();
        Double max=0d;
        Individual best=null;
        Individual contender;

        for (int i = 0; i < Math.min(amount,candidates.size()); i++) {
            for (int j = 0; j < tourneySize; j++) {
                contender = candidates.get(random.nextInt(candidates.size()));
                if(contender.getFitness()>max){
                    max = contender.getFitness();
                    best = contender;
                }
            }
            if(best != null){
                max = 0d;
                champions.add(best);
            }
        }

        return champions;
    }
}
