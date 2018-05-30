package genes;

import interfaces.Phenotype;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerPhenotype that = (IntegerPhenotype) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, name);
    }
}
