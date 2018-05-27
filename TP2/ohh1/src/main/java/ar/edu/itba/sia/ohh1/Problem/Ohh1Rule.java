package ar.edu.itba.sia.ohh1.Problem;

import ar.com.itba.sia.Rule;

import java.util.function.Function;

public class Ohh1Rule implements Rule<Ohh1State> {

    private double cost;
    private Function<Ohh1State,Ohh1State> applyFunc;
    private Function<Ohh1State,Boolean> isApplicableFunc;

    public Ohh1Rule(double cost,Function<Ohh1State,Ohh1State> applyFunc,Function<Ohh1State,Boolean> isApplicableFunc) {
        this.cost = cost;
        this.applyFunc = applyFunc;
        this.isApplicableFunc = isApplicableFunc;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public void setCost(double v) {
        cost = v;
    }

    @Override
    public Ohh1State applyToState(Ohh1State ohh1State) {
        return applyFunc.apply(ohh1State);
    }
    public Boolean isApplicable(Ohh1State ohh1State){
        return isApplicableFunc.apply(ohh1State);
    }
}
