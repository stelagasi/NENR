package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.evaluator;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.BinaryIndividual;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.DoubleIndividual;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Math.pow;

public class BinaryPopulationEvaluator implements IPopulationEvaluator<BinaryIndividual>{
    private BinaryIndividual worstIndividual;
    private BinaryIndividual bestIndividual;
    private final int lowerBound;
    private final int upperBound;

    public BinaryPopulationEvaluator(int lowerBound, int upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public double evaluatePenalty(List<BinaryIndividual> population) {
        double populationPenalty = 0.0;

        BinaryIndividual worstIndividual = population.get(0);
        BinaryIndividual bestIndividual = population.get(0);

        worstIndividual.setPenalty(-MAX_VALUE);
        bestIndividual.setPenalty(MAX_VALUE);

        for (BinaryIndividual individual : population) {
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

    public double evaluateFitness(List<BinaryIndividual> population) {
        evaluatePenalty(population);
        double worstPenalty = getWorstIndividual().getPenalty();
        double populationFitness = 0.0;
        for (BinaryIndividual individual : population) {
            double fitness = worstPenalty - individual.getPenalty();
            populationFitness += fitness;
            individual.setPenalty(fitness);
        }
        return populationFitness;
    }

    @Override
    public BinaryIndividual getBestIndividual() {
        return bestIndividual;
    }

    @Override
    public BinaryIndividual getWorstIndividual() {
        return worstIndividual;
    }

    public double evaluatePenaltyOfIndividual(BinaryIndividual individual) {
//        var penalty = Math.abs(goalFunction.valueAt(getDoubleRepresentation(individual).getChromosomes()));
//        individual.setPenalty(penalty);
        return 0;
    }

    public DoubleIndividual getDoubleRepresentation(BinaryIndividual individual){
        List<List<Boolean>> binaryChromosomes = individual.getChromosomes();
        List<Double> doubleChromosomes = new ArrayList<>(binaryChromosomes.size());

        for (List<Boolean> binaryChromosome : binaryChromosomes) {
            double b = 0;
            for (int i = 0; i < binaryChromosome.size(); i++) {
                if(binaryChromosome.get(i)){
                    b += pow(2, i);
                }
            }
            doubleChromosomes.add(lowerBound + b * (upperBound - lowerBound) / (pow(2, binaryChromosome.size()) - 1));
        }
        DoubleIndividual doubleRepresentation = new DoubleIndividual(doubleChromosomes);
        doubleRepresentation.setPenalty(individual.getPenalty());
        return doubleRepresentation;
    }

    public void setWorstIndividual(BinaryIndividual worstIndividual) {
        this.worstIndividual = worstIndividual;
    }

    public void setBestIndividual(BinaryIndividual bestIndividual) {
        this.bestIndividual = bestIndividual;
    }
}
