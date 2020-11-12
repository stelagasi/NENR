package hr.fer.nenr.geneticalgorithm;

import java.util.ArrayList;
import java.util.List;

public class EliminationGeneticAlgorithm extends GeneticAlgorithm {
    private static final double MORTALITY = 0.5;
    private static final int NUMBER_OF_INDIVIDUALS_TO_SELECT = 3;

    public EliminationGeneticAlgorithm(IFunction goalFunction, PopulationEvaluator populationEvaluator) {
        super(goalFunction, populationEvaluator);
    }

    @Override
    public List<Individual> execute(int numberOfIterations) {
        this.setPopulation(generateStartingPopulation());
        PopulationEvaluator populationEvaluator = this.getPopulationEvaluator();

        for (int i = numberOfIterations; i > 0; i--) {
            double populationFitness = populationEvaluator.evaluate(this.getPopulation(), this.getGoalFunction());
            List<Individual> parents = selection((int) (MORTALITY * getNumberInPopulation()) * 2, populationFitness);
            List<Individual> children = reproduction(parents);
            mutation(children);
            this.getPopulation().addAll(children);
        }
        return this.getPopulation();
    }

    @Override
    List<Individual> selection(int parentsNeeded, double populationFitness) {
        List<Individual> population = this.getPopulation();
        List<Individual> parents = new ArrayList<>(parentsNeeded);

        for (int i = 0; i < parentsNeeded; i += NUMBER_OF_INDIVIDUALS_TO_SELECT) {
            List<Individual> triples = new ArrayList<>(NUMBER_OF_INDIVIDUALS_TO_SELECT);
            for (int j = 0; j < NUMBER_OF_INDIVIDUALS_TO_SELECT; j++) {
                triples.add(population.get(chooseIndividual(populationFitness)));
            }
            Individual worstOfThree = triples.get(0);
            for (int k = 1; k < NUMBER_OF_INDIVIDUALS_TO_SELECT; k++) {
                Individual individual = triples.get(k);
                if (individual.getFitness() > worstOfThree.getFitness()) {
                    worstOfThree = individual;
                }
                population.remove(worstOfThree);
                parents.addAll(triples);
            }
            triples.clear();
        }
        return parents;
    }
}
