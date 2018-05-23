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
    public Float getValue(String key) {
        return value*1.0f;
    }

    @Override
    public String toString() {
        return "[" + name + ":" + value + "]";
    }
}
