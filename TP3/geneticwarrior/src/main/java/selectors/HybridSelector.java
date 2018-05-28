package selectors;


import individuals.Individual;
import interfaces.Selector;

import java.util.ArrayList;
import java.util.List;

public class HybridSelector implements Selector {

    private Selector first;
    private Selector second;
    private Double firstPercentage;
    private Double secondPercentage;

    public HybridSelector(Selector first,Selector second, Double firstPercentage, Double secondPercentage){
        this.first = first;
        this.second = second;
        this.firstPercentage = firstPercentage;
        this.secondPercentage = secondPercentage;
    }
    @Override
    public List<Individual> selectChampions(List<Individual> candidates, Integer amount, Integer generation) {
        long firstAmount = (int)Math.floor(amount*firstPercentage);
        long secondAmount = (int)Math.ceil(amount * secondPercentage);
        ArrayList<Individual> ans = new ArrayList<Individual>();
        ans.addAll(first.selectChampions(candidates,(int)firstAmount,generation));
        ans.addAll(second.selectChampions(candidates,(int)secondAmount,generation));
        return ans;
    }
}
