package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.evaluator.IPopulationEvaluator;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.Individual;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.initializer.IPopulationInitializer;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.mutator.IMutator;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.reproductioner.IReproductioner;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.selectioner.ISelectioner;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingDouble;

public class EliminationGeneticAlgorithm<T extends Individual<?>> extends GeneticAlgorithm<T> {

    private final int numberOfIndividualsToSelect;
    private T bestIndividual;

    public EliminationGeneticAlgorithm(IPopulationInitializer<T> populationInitializer, IMutator<T> mutator, IReproductioner<T> reproductioner, ISelectioner<T> selectioner, IPopulationEvaluator<T> populationEvaluator, int numberOfIndividualsToSelect) {
        super(populationInitializer, mutator, reproductioner, selectioner, populationEvaluator);
        this.numberOfIndividualsToSelect = numberOfIndividualsToSelect;
    }

    @Override
    public T execute(int numberOfIterations, double epsilon) {
        population = populationInitializer.initialize();
        population.forEach(populationEvaluator::evaluatePenalty);

        for (int iteration = 0; iteration < numberOfIterations; iteration++) {
            for (int i = 0; i < population.size(); i++) {
                population.sort(Comparator.comparing(e -> e.getPenalty()));

                if (i == population.size() - 1) System.out.println(iteration + " " + population.get(0));
                if (population.get(0).getPenalty() < epsilon) return bestIndividual = population.get(0);

                List<T> tournament = IntStream.range(0, numberOfIndividualsToSelect).mapToObj(e -> selectioner.select(population)).sorted(comparingDouble(o -> o.getPenalty())).collect(Collectors.toList());

                T child = reproductioner.reproduce(tournament.get(0), tournament.get(1));
                mutator.mutate(child);
                populationEvaluator.evaluatePenalty(child);

                if (child.getPenalty() < tournament.get(tournament.size() - 1).getPenalty()) {
                    population.remove(tournament.get(tournament.size() - 1));
                    population.add(child);
                }
            }
        }
        population.sort(Comparator.comparing(e -> e.getPenalty()));
        return bestIndividual = population.get(0);
    }

    @Override
    public void test() {
        if (bestIndividual == null) throw new IllegalStateException("GA is not trained yet.");
        populationEvaluator.evaluate(bestIndividual);
    }
}
