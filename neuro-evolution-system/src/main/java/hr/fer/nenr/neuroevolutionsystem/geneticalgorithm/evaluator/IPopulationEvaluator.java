package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.evaluator;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.DoubleIndividual;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.IIndividual;

public interface IPopulationEvaluator<T extends IIndividual<?>> {

    void evaluatePenalty(T individual);

    void evaluate(T individual);

}
