package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.IIndividual;

public interface IGeneticAlgorithm<T extends IIndividual<?>> {
    T execute(int numberOfIterations, int numberOfEvaluations);
}
