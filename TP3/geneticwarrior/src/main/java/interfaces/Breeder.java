package interfaces;

import individuals.Individual;

import java.util.List;

public interface Breeder {
    List<Individual> breedChampions(List<Individual> champions);
}
