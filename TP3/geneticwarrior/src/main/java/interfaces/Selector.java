package interfaces;

import individuals.Individual;

import java.util.List;

public interface Selector {
    List<Individual> selectChampions(List<Individual> candidates);
}
