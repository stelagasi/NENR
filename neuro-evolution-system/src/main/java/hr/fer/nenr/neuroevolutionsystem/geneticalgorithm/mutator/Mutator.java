package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.mutator;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.IIndividual;

import java.util.List;

public abstract class Mutator<T extends IIndividual<?>> implements IMutator<T> {
    protected final double mutationProbability;
    protected final int mutationRange;

    public Mutator() {
        this.mutationProbability = 0.5;
        this.mutationRange = 1;
    }

    public Mutator(double mutationProbability, int mutationRange) {
        this.mutationProbability = mutationProbability;
        this.mutationRange = mutationRange;
    }

    @Override
    public void mutateMultiple(List<T> children) {
        for (T child : children){
            mutate(child);
        }
    }
}
