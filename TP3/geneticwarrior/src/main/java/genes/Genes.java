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
}
