package selectors;

import individuals.Individual;
import interfaces.Selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EliteSelector extends BaseSelector implements Selector {

    @Override
    public List<Individual> selectChampions(List<Individual> candidates, Integer amount, Integer generation) {
        if(candidates.size() <= 1){
            return candidates;
        }

        List<Individual> filteredChampions = new ArrayList<>(candidates);
        filteredChampions.sort(Comparator.comparingDouble(o->-o.getFitness()));
        List<Individual> champions = filteredChampions.subList(0,Math.min(amount,filteredChampions.size()));
        return champions;
    }
}
