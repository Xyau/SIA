package genes;

import interfaces.Phenotype;

import java.util.*;

public class Genes {
    final Map<String, Phenotype> phenotypes=new HashMap<>();

    public Genes( Collection<Phenotype> phenotypes) {
        phenotypes.stream().forEach(phenotype ->
                this.phenotypes.put(phenotype.getName(),phenotype));
    }

    public Collection<Phenotype> getAllPhenotypes(){
        return phenotypes.values();
    }
    public Phenotype getPhenotypeByName(String name){
        return phenotypes.get(name);
    }

    @Override
    public String toString() {
        return phenotypes.values().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;
        Genes genes = (Genes) o;
        return Objects.equals(phenotypes, genes.phenotypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phenotypes);
    }
}
