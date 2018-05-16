package selectors;

import individuals.Individual;
import interfaces.Selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TopNSelector implements Selector {
    private Integer selectedChampions;

    public TopNSelector(Integer selectedChampions) {
        this.selectedChampions = selectedChampions;
    }

    @Override
    public List<Individual> selectChampions(List<Individual> candidates) {
        candidates.sort(Comparator.comparingInt(o->o.getFitness().intValue()));
        List<Individual> champions = candidates.subList(0,Math.max(selectedChampions,candidates.size()-1));
        return champions;
    }
}
