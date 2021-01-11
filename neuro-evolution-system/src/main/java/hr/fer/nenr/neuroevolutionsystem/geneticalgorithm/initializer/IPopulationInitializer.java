package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.initializer;


import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.IIndividual;

import java.util.List;

public interface IPopulationInitializer<T extends IIndividual<?>> {
    List<T> initialize();

    int getPopulationSize();

    int getNumberOfChromosomes();
}
