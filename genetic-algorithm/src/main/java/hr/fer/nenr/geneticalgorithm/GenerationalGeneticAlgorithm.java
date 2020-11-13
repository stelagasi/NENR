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

        for (int i = 0; i < numberOfIterations; i++) {
            int individualsNeeded = getInitialPopulationSize();
            List<Individual> nextPopulation = new ArrayList<>(individualsNeeded);
            double populationFitness = populationEvaluator.evaluatePenalty(this.getPopulation(), this.getGoalFunction());
            System.out.println(i + " " + populationEvaluator.getBestIndividual());
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

    @Override
    protected List<Individual> selection(int parentsNeeded, double populationFitness) {
        List<Individual> parents = new ArrayList<>(parentsNeeded);
        List<Individual> copy = new ArrayList<>(getNumberInPopulation());
        for (int i = 0; i < getNumberInPopulation(); i++) {
            copy.add(new Individual(getPopulation().get(i).getChromosomes()));
        }
        getPopulationEvaluator().evaluateFitness(copy, getGoalFunction());

        for (int i = 0; i < parentsNeeded; i++) {
            parents.add(this.getPopulation().get(chooseIndividual(copy, populationFitness)));
        }
        return parents;
    }

    private List<Individual> multipleReproduction(List<Individual> parents) {
        List<Individual> children = new ArrayList<>(parents.size() / 2);
        for (int i = 0; i < parents.size(); i += 2) {
            children.add(reproduction(List.of(parents.get(i), parents.get(i + 1))));
        }
        return children;
    }

    private int chooseIndividual(List<Individual> population, double populationFitness) {
        Random random = new Random();
        int chosen = 0;
        double limit = random.nextDouble() * populationFitness;
        double upperLimit = population.get(0).getPenalty();
        while (limit > upperLimit && chosen < population.size() - 1) {
            upperLimit += population.get(++chosen).getPenalty();
        }
        return chosen;
    }
}
