package hr.fer.nenr.geneticalgorithm;

import java.util.List;

import static java.lang.Math.pow;

public class PopulationEvaluator {
    private List<InputData> dataset;

    public PopulationEvaluator(List<InputData> dataset) {
        this.dataset = dataset;
    }

    public double evaluate(List<Individual> population, IFunction goalFunction) {
        double populationFitness = 0.0;
        for (Individual individual : population) {
            double sum = 0.0;
            for (InputData data : dataset) {
                sum += pow(data.getResult() - goalFunction.valueAt(data.getX(), data.getY(), individual.getChromosomes()), 2);
            }
            double individualFitness = sum / dataset.size();
            individual.setFitness(individualFitness);
            populationFitness += individualFitness;
        }
        return populationFitness;
    }
}
