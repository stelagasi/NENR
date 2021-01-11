package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.evaluator;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.IIndividual;

import java.util.List;

public interface IPopulationEvaluator<T extends IIndividual<?>> {
    double evaluatePenalty(List<T> population);

    double evaluateFitness(List<T> population);

    T getBestIndividual();

    T getWorstIndividual();

    double evaluatePenaltyOfIndividual(T individual);
}
