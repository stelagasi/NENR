package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.mutator;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.DoubleIndividual;

import java.util.List;
import java.util.Random;

public class DoubleMutator extends Mutator<DoubleIndividual> {

    public DoubleMutator(double mutationProbability, int mutationRange) {
        super(mutationProbability, mutationRange);
    }

    @Override
    public void mutate(DoubleIndividual child) {
        Random random = new Random();
        List<Double> childChromosomes = child.getChromosomes();

        for (int i = 0; i < childChromosomes.size(); i++) {
            if (random.nextDouble() < mutationProbability) {
                double mutation = random.nextDouble() * (mutationRange + mutationRange) - mutationRange;
                childChromosomes.set(i, childChromosomes.get(i) + mutation);
            }
        }
    }
}
