package hr.fer.nenr.geneticalgorithm;

import java.util.ArrayList;
import java.util.List;

public class GenerationalGeneticAlgorithm extends GeneticAlgorithm {
    private final boolean elitism;

    public GenerationalGeneticAlgorithm(IFunction goalFunction, PopulationEvaluator populationEvaluator, boolean elitism) {
        super(goalFunction, populationEvaluator);
        this.elitism = elitism;
    }

    @Override
    public List<Individual> execute(int numberOfIterations) {
        this.setPopulation(generateStartingPopulation());
        PopulationEvaluator populationEvaluator = this.getPopulationEvaluator();

        for (int i = numberOfIterations; i > 0; i--) {
            int individualsNeeded = getInitialPopulationSize();
            List<Individual> nextPopulation = new ArrayList<>(individualsNeeded);
            double populationFitness = populationEvaluator.evaluate(this.getPopulation(), this.getGoalFunction());
            if (elitism) {
                nextPopulation.add(findBestIndividual(populationFitness));
                individualsNeeded--;
            }
            List<Individual> parents = selection(2 * individualsNeeded, populationFitness);
            List<Individual> children = reproduction(parents);
            mutation(children);
            nextPopulation.addAll(children);
            this.setPopulation(nextPopulation);
        }
        return this.getPopulation();
    }

    @Override
    List<Individual> selection(int parentsNeeded, double populationFitness) {
        List<Individual> parents = new ArrayList<>(parentsNeeded);

        for (int i = 0; i < parentsNeeded; i++) {
            parents.add(this.getPopulation().get(chooseIndividual(populationFitness)));
        }
        return parents;
    }

    private Individual findBestIndividual(double populationFitness) {
        List<Individual> population = this.getPopulation();
        Individual bestIndividual = population.get(0);
        for (int i = 1; i < population.size(); i++) {
            Individual individual = population.get(i);
            if (individual.getFitness() < bestIndividual.getFitness()) bestIndividual = individual;
        }
        return bestIndividual;
    }
}
