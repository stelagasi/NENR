package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.evaluator;

import hr.fer.nenr.neuroevolutionsystem.ArtificialNeuralNetwork;
import hr.fer.nenr.neuroevolutionsystem.Dataset;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.DoubleIndividual;

import java.util.List;

import static java.lang.Double.MAX_VALUE;

public class DoublePopulationEvaluator implements IPopulationEvaluator<DoubleIndividual> {
    private final ArtificialNeuralNetwork ann;
    private final Dataset dataset;
    private DoubleIndividual worstIndividual;
    private DoubleIndividual bestIndividual;

    public DoublePopulationEvaluator(ArtificialNeuralNetwork ann, Dataset dataset){
        this.ann = ann;
        this.dataset = dataset;
    }

    public double evaluatePenalty(List<DoubleIndividual> population) {
        double populationPenalty = 0.0;

        DoubleIndividual worstIndividual = population.get(0);
        DoubleIndividual bestIndividual = population.get(0);

        worstIndividual.setPenalty(-MAX_VALUE);
        bestIndividual.setPenalty(MAX_VALUE);

        for (DoubleIndividual individual : population) {
            double individualPenalty = evaluatePenaltyOfIndividual(individual);

            if (worstIndividual.getPenalty() < individualPenalty) {
                worstIndividual = individual;
            }
            if (bestIndividual.getPenalty() > individualPenalty) {
                bestIndividual = individual;
            }

            individual.setPenalty(individualPenalty);
            populationPenalty += individualPenalty;
        }

        this.setWorstIndividual(worstIndividual);
        this.setBestIndividual(bestIndividual);

        return populationPenalty;
    }

    public double evaluateFitness(List<DoubleIndividual> population) {
        evaluatePenalty(population);
        double worstPenalty = getWorstIndividual().getPenalty();
        double populationFitness = 0.0;
        for (DoubleIndividual individual : population) {
            double fitness = worstPenalty - individual.getPenalty();
            populationFitness += fitness;
            individual.setPenalty(fitness);
        }
        return populationFitness;
    }

    public double evaluatePenaltyOfIndividual(DoubleIndividual individual) {
        return ann.calculateError(individual, dataset);
    }

    public DoubleIndividual getWorstIndividual() {
        return worstIndividual;
    }

    public void setWorstIndividual(DoubleIndividual worstIndividual) {
        this.worstIndividual = worstIndividual;
    }

    public DoubleIndividual getBestIndividual() {
        return bestIndividual;
    }

    public void setBestIndividual(DoubleIndividual bestIndividual) {
        this.bestIndividual = bestIndividual;
    }
}
