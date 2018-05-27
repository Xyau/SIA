package genes;

import interfaces.Phenotype;
import interfaces.Genotype;

import java.util.Random;

public class IntegerGenotype implements Genotype {
    final Integer min;
    final Integer max;
    final String name;

    public IntegerGenotype(Integer min, Integer max, String name) {
        this.min = min;
        this.max = max;
        this.name = name;
    }

    @Override
    public Phenotype getMutation(Phenotype phenotype, Double mutationRate) {
        Phenotype mutatedPhenotype = phenotype;
        if(mutationRate>0.4 && phenotype.getValue("") < max-1){
            mutatedPhenotype = new IntegerPhenotype(name,phenotype.getValue("").intValue()+1);
        } else if( mutationRate < -0.4 && phenotype.getValue("") > min+1){
            mutatedPhenotype = new IntegerPhenotype(name,phenotype.getValue("").intValue()-1);
        }
        return mutatedPhenotype;
    }

    @Override
    public Phenotype getRandomPhenotype(Random random) {
        return new IntegerPhenotype(name,random.nextInt(max-min)+min);
    }

    @Override
    public String getName() {
        return name;
    }

   }
