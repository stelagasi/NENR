package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.mutator;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.IIndividual;

import java.util.List;

public interface IMutator<T extends IIndividual<?>> {
    void mutateMultiple(List<T> children);

    void mutate(T child);
}
