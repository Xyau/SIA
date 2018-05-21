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
    public List<Individual> selectChampions(List<Individual> candidates) {
        List<Individual> champions = new ArrayList<>();
        Double max=0d;
        Individual best=null;
        Individual contender;

        for (int i = 0; i < selectedIndividuals; i++) {
            for (int j = 0; j < tourneySize; j++) {
                contender = candidates.get(random.nextInt(candidates.size()-1));
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
