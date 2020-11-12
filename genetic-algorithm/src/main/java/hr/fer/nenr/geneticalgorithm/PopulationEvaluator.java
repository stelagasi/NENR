package hr.fer.nenr.geneticalgorithm;

import java.util.List;

import static java.lang.Double.*;
import static java.lang.Math.pow;

public class PopulationEvaluator {
    private List<InputData> dataset;
    private Individual worstIndividual;
    private Individual bestIndividual;

    public PopulationEvaluator(List<InputData> dataset) {
        this.dataset = dataset;
    }

    public double evaluatePenalty(List<Individual> population, IFunction goalFunction) {
        double populationPenalty = 0.0;

        Individual worstIndividual = population.get(0);
        Individual bestIndividual = population.get(0);

        worstIndividual.setPenalty(-MAX_VALUE);
        bestIndividual.setPenalty(MAX_VALUE);

        for (Individual individual : population) {
            double individualPenalty = evaluatePenaltyOfIndividual(individual, goalFunction);

            if(worstIndividual.getPenalty() < individualPenalty){
                worstIndividual = individual;
            }
            if(bestIndividual.getPenalty() > individualPenalty){
                bestIndividual = individual;
            }

            individual.setPenalty(individualPenalty);
            populationPenalty += individualPenalty;
        }

        this.worstIndividual = worstIndividual;
        this.bestIndividual = bestIndividual;

        return populationPenalty;
    }

    public double evaluateFitness(List<Individual> population, IFunction goalFunction) {
        evaluatePenalty(population, goalFunction);
        double worstPenalty = worstIndividual.getPenalty();
        double populationFitness = 0.0;
        for(Individual individual : population){
            double fitness = worstPenalty - individual.getPenalty();
            populationFitness += fitness;
            individual.setPenalty(fitness);
        }
        return populationFitness;
    }

    public double evaluatePenaltyOfIndividual(Individual individual, IFunction goalFunction){
        double sum = 0.0;
        for (InputData data : dataset) {
            sum += pow(data.getResult() - goalFunction.valueAt(data.getX(), data.getY(), individual.getChromosomes()), 2);
        }
        return sum / dataset.size();
    }

    public Individual getWorstIndividual() {
        return worstIndividual;
    }

    public Individual getBestIndividual() {
        return bestIndividual;
    }
}
