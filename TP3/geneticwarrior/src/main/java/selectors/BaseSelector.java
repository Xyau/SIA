package selectors;

import interfaces.Selector;

public abstract class BaseSelector implements Selector {
    Integer selectedIndividuals;

    public BaseSelector(Integer selectedIndividuals) {
        this.selectedIndividuals = selectedIndividuals;
    }
}
