package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.mutator;


import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.DoubleIndividual;

import java.util.List;
import java.util.Random;

public class GaussianDoubleMutator extends Mutator<DoubleIndividual> {

    public GaussianDoubleMutator(double mutationProbability, double mutationRange) {
        super(mutationProbability, mutationRange);
    }

    @Override
    public void mutate(DoubleIndividual child) {
        Random random = new Random();
        List<Double> childChromosomes = child.getChromosomes();

        for (int i = 0; i < childChromosomes.size(); i++) {
            if (random.nextDouble() < mutationProbability) {
                childChromosomes.set(i, childChromosomes.get(i) + random.nextGaussian() * mutationRange);
            }
        }
    }
}
