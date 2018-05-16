package genes;

import interfaces.Phenotype;

public class IntegerPhenotype implements Phenotype {
    final Integer value;
    final String name;

    public IntegerPhenotype(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getValue(String key) {
        return value*1.0;
    }

    @Override
    public String toString() {
        return "[" + name + ":" + value + "]";
    }
}
