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
        List<Individual> champions = new ArrayList<>();
        champions.addAll(candidates);
        Collections.sort(champions, Comparator.comparingInt(
                o -> o.getFitness().intValue()));
        return champions.stream().limit(selectedChampions).collect(Collectors.toList());
    }
}
