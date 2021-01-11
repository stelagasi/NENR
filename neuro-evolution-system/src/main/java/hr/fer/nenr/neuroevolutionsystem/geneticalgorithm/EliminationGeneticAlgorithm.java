package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.evaluator.IPopulationEvaluator;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.Individual;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.initializer.IPopulationInitializer;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.mutator.IMutator;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.reproductioner.IReproductioner;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.selectioner.ISelectioner;

import java.util.List;
import java.util.stream.IntStream;

public class EliminationGeneticAlgorithm<T extends Individual<?>> extends GeneticAlgorithm<T> {

    private final int numberOfIndividualsToSelect;

    public EliminationGeneticAlgorithm(IPopulationInitializer<T> populationInitializer, IMutator<T> mutator, IReproductioner<T> reproductioner, ISelectioner<T> selectioner, IPopulationEvaluator<T> populationEvaluator, int numberOfIndividualsToSelect) {
        super(populationInitializer, mutator, reproductioner, selectioner, populationEvaluator);
        this.numberOfIndividualsToSelect = numberOfIndividualsToSelect;
    }

    @Override
    public T execute(int numberOfIterations, int numberOfEvaluations) {
        population = populationInitializer.initialize();
//        population.forEach(e -> populationEvaluator.evaluatePenaltyOfIndividual(e, goalFunction));
//        for (int i = 0; goalFunction.getNumberOfEvaluations() < numberOfEvaluations; i++) {
//            population.sort(Comparator.comparing(e -> e.getPenalty()));
//            //   System.out.println(i + " " + population.get(0));
//
//            List<Integer> selected = IntStream.range(0, numberOfIndividualsToSelect).mapToObj(e -> (int) (Math.random() * population.size())).collect(Collectors.toList());
//            var sorted = selected.stream().sorted(comparingDouble(a -> population.get(a).getPenalty())).collect(Collectors.toList());
//
//            var child = reproductioner.reproduce(population.get(sorted.get(0)), population.get(sorted.get(1)));
//            mutator.mutate(List.of(child));
//            populationEvaluator.evaluatePenaltyOfIndividual(child, goalFunction);
//            if (child.getPenalty() < population.get(sorted.get(sorted.size() - 1)).getPenalty()) {
//                population.set(sorted.get(sorted.size() - 1), child);
//            }
//        }
//        population.sort(Comparator.comparing(e -> e.getPenalty()));
//        return population.get(0);
        return null;
    }
}
