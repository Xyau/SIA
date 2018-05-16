package genes;

import interfaces.Genotype;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Species {
    final String name;
    final List<Genotype> genotypes;

    public Species(String name, List<Genotype> genotypes) {
        this.name = name;
        this.genotypes = genotypes;
    }

    public String getName() {
        return name;
    }

    public List<Genotype> getGenotypes(){
        return genotypes;
    }

    public Genes getRandomGenes(Random random){
        return new Genes(genotypes.stream().map(genotype ->
                genotype.getRandomPhenotype(random))
                .collect(Collectors.toList()));
    }
}
