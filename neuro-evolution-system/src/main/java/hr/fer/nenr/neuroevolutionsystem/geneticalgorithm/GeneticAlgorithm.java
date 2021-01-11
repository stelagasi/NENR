package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.evaluator.IPopulationEvaluator;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.IIndividual;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.initializer.IPopulationInitializer;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.mutator.IMutator;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.reproductioner.IReproductioner;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.selectioner.ISelectioner;

import java.util.List;

public abstract class GeneticAlgorithm<T extends IIndividual<?>> implements IGeneticAlgorithm<T> {
    protected List<T> population;
    protected final IPopulationInitializer<T> populationInitializer;
    protected final IMutator<T> mutator;
    protected final IReproductioner<T> reproductioner;
    protected final ISelectioner<T> selectioner;
    protected final IPopulationEvaluator<T> populationEvaluator;

    public GeneticAlgorithm(IPopulationInitializer<T> populationInitializer, IMutator<T> mutator, IReproductioner<T> reproductioner, ISelectioner<T> selectioner, IPopulationEvaluator<T> populationEvaluator) {
        this.populationInitializer = populationInitializer;
        this.mutator = mutator;
        this.reproductioner = reproductioner;
        this.selectioner = selectioner;
        this.populationEvaluator = populationEvaluator;
    }

    public void setPopulation(List<T> population) {
        this.population = population;
    }
}
