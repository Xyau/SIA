package selectors;


import individuals.Individual;
import interfaces.Selector;

import java.util.ArrayList;
import java.util.List;

public class HybridSelector implements Selector {

    private Selector first;
    private Selector second;
    private Double firstToTotalRatio;

    public HybridSelector(Selector first,Selector second, Double firstToTotalRatio){
        if(firstToTotalRatio >1 || firstToTotalRatio < 0){
            throw new IllegalArgumentException("Ratio must be between 0 and 1");
        }
        this.first = first;
        this.second = second;
        this.firstToTotalRatio = firstToTotalRatio;
    }
    @Override
    public List<Individual> selectChampions(List<Individual> candidates, Integer amount, Integer generation) {
        long firstAmount = (int)Math.floor(amount*firstToTotalRatio);
        long secondAmount = amount-firstAmount;
        ArrayList<Individual> ans = new ArrayList<Individual>();
        ans.addAll(first.selectChampions(candidates,(int)firstAmount,generation));
        ans.addAll(second.selectChampions(candidates,(int)secondAmount,generation));
        return ans;
    }
}
