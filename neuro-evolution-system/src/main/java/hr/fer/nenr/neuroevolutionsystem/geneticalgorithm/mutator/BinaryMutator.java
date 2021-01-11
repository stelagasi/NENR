package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.mutator;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.BinaryIndividual;

import java.util.List;
import java.util.Random;

public class BinaryMutator extends Mutator<BinaryIndividual> {

    public BinaryMutator(double mutationProbability, int mutationRange) {
        super(mutationProbability, mutationRange);
    }

    @Override
    public void mutate(BinaryIndividual child) {
        Random random = new Random();
        List<List<Boolean>> childChromosomes = child.getChromosomes();
        for (List<Boolean> childChromosome : childChromosomes) {
            if (random.nextDouble() < mutationProbability) {
                int index = random.nextInt(childChromosome.size());
                childChromosome.set(index, !childChromosome.get(index));
            }
        }
    }
}
