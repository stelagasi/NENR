package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.selectioner;


import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.IIndividual;

import java.util.List;
import java.util.Random;

public class RandomSelectioner<T extends IIndividual<?>> implements ISelectioner<T> {
    @Override
    public T select(List<T> population) {
        Random random = new Random();
        return population.get(random.nextInt(population.size()));
    }
}
