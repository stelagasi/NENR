package hr.fer.nenr.geneticalgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
            double populationFitness = populationEvaluator.evaluateFitness(this.getPopulation(), this.getGoalFunction());
            System.out.println(populationEvaluator.getBestIndividual());
            if (elitism) {
                nextPopulation.add(populationEvaluator.getBestIndividual());
                individualsNeeded--;
            }
            List<Individual> parents = selection(2 * individualsNeeded, populationFitness);
            List<Individual> children = multipleReproduction(parents);
            mutation(children);
            nextPopulation.addAll(children);
            this.setPopulation(nextPopulation);
        }
        return this.getPopulation();
    }

    List<Individual> multipleReproduction(List<Individual> parents) {
        List<Individual> children = new ArrayList<>(parents.size() / 2);
        for (int i = 0; i < parents.size(); i += 2) {
            children.add(reproduction(List.of(parents.get(i), parents.get(i + 1))));
        }
        return children;
    }

    @Override
    List<Individual> selection(int parentsNeeded, double populationFitness) {
        List<Individual> parents = new ArrayList<>(parentsNeeded);

        for (int i = 0; i < parentsNeeded; i++) {
            parents.add(this.getPopulation().get(chooseIndividual(populationFitness)));
        }
        return parents;
    }

    private int chooseIndividual(double populationFitness) {
        Random random = new Random();
        int chosen = 0;
        double limit = random.nextDouble() * populationFitness;
        double upperLimit = this.getPopulation().get(0).getPenalty();
        while (limit > upperLimit && chosen < getPopulation().size() - 1) {
            upperLimit += getPopulation().get(++chosen).getPenalty();
        }
        return chosen;
    }
}
