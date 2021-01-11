package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.mutator;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.DoubleIndividual;

import java.util.List;
import java.util.Random;

public class GaussianReplaceDoubleMutator extends Mutator<DoubleIndividual> {

    public GaussianReplaceDoubleMutator(double mutationProbability, int mutationRange) {
        super(mutationProbability, mutationRange);
    }

    @Override
    public void mutate(DoubleIndividual child) {
        Random random = new Random();
        List<Double> childChromosomes = child.getChromosomes();

        for (int i = 0; i < childChromosomes.size(); i++) {
            if (random.nextDouble() < mutationProbability) {
                childChromosomes.set(i, random.nextGaussian() * mutationRange);
            }
        }
    }
}

