package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.selectioner;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.IIndividual;

import java.util.List;

public interface ISelectioner<T extends IIndividual<?>> {
    T select(List<T> population);
}
