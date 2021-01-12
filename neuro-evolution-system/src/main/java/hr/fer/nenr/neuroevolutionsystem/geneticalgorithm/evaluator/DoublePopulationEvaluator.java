package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.evaluator;

import hr.fer.nenr.neuroevolutionsystem.ArtificialNeuralNetwork;
import hr.fer.nenr.neuroevolutionsystem.Dataset;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.DoubleIndividual;

public class DoublePopulationEvaluator implements IPopulationEvaluator<DoubleIndividual> {
    private final ArtificialNeuralNetwork ann;
    private final Dataset dataset;

    public DoublePopulationEvaluator(ArtificialNeuralNetwork ann, Dataset dataset) {
        this.ann = ann;
        this.dataset = dataset;
    }

    @Override
    public void evaluatePenalty(DoubleIndividual individual) {
        individual.setPenalty(ann.calculateError(individual, dataset));
    }

    @Override
    public void evaluate(DoubleIndividual individual){
        ann.testGA(individual, dataset);
    }
}
