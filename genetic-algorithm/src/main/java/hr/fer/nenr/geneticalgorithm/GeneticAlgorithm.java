package hr.fer.nenr.geneticalgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class GeneticAlgorithm {
    private static final int SIZE_OF_INDIVIDUAL = 5;
    private static final int INITIAL_POPULATION_SIZE = 20;
    private static final double MIN_CHROMOSOME_VALUE = -4.0;
    private static final double MAX_CHROMOSOME_VALUE = 4.0;
    private static final double MUTATION_PROBABILITY = 0.01;
    private static final int MUTATION_RANGE = 1;
    private List<Individual> population;
    private final IFunction goalFunction;
    private final PopulationEvaluator populationEvaluator;

    public GeneticAlgorithm(IFunction goalFunction, PopulationEvaluator populationEvaluator) {
        this.goalFunction = goalFunction;
        this.populationEvaluator = populationEvaluator;
    }

    public abstract List<Individual> execute(int numberOfIterations);

    void mutation(List<Individual> children) {
        Random random = new Random();
        double mutationProbability = getMutationProbability();

        for (Individual child : children) {
            List<Double> childChromosomes = child.getChromosomes();
            for (int i = 0; i < childChromosomes.size(); i++) {
                if (random.nextDouble() < mutationProbability) {
                    double mutation = random.nextDouble() * (getMutationRange() + getMutationRange()) - getMutationRange();
                    childChromosomes.set(i, childChromosomes.get(i) + mutation);
                }
            }
        }
    }

    List<Individual> reproduction(List<Individual> parents) {
        List<Individual> children = new ArrayList<>(parents.size() / 2);
        Random random = new Random();
        for (int i = 0; i < parents.size(); i += 2) {
            List<Double> firstParentChromosomes = parents.get(i).getChromosomes();
            List<Double> secondParentChromosomes = parents.get(i + 1).getChromosomes();
            int parentChromosomesSize = firstParentChromosomes.size();

            if (parentChromosomesSize != secondParentChromosomes.size()) {
                throw new IllegalArgumentException("Parents not the same size");
            }

            List<Double> childChromosomes = new ArrayList<>(parentChromosomesSize);
            for (int j = 0; j < firstParentChromosomes.size(); j++) {
                int whichParent = random.nextInt(2);
                if (whichParent == 0) {
                    childChromosomes.add(firstParentChromosomes.get(j));
                } else {
                    childChromosomes.add(secondParentChromosomes.get(j));
                }
            }
            children.add(new Individual(childChromosomes));
        }
        return children;
    }

    abstract List<Individual> selection(int parentsNeeded, double populationFitness);

    List<Individual> generateStartingPopulation() {
        List<Individual> startingPopulation = new ArrayList<>(INITIAL_POPULATION_SIZE);
        Random random = new Random();

        for (int i = 0; i < INITIAL_POPULATION_SIZE; i++) {
            List<Double> chromosomes = new ArrayList<>(SIZE_OF_INDIVIDUAL);
            for (int j = 0; j < SIZE_OF_INDIVIDUAL; j++) {
                chromosomes.add(random.nextDouble() * (MAX_CHROMOSOME_VALUE - MIN_CHROMOSOME_VALUE) + MIN_CHROMOSOME_VALUE);
            }
            startingPopulation.add(new Individual(chromosomes));
        }

        return startingPopulation;
    }

    int chooseIndividual(double populationFitness) {
        Random random = new Random();
        int chosen = 0;
        double limit = random.nextDouble() * populationFitness;
        double upperLimit = population.get(0).getFitness();
        while (limit > upperLimit && chosen < population.size() - 1) {
            upperLimit += population.get(++chosen).getFitness();
        }
        return chosen;
    }

    public int getNumberInPopulation() {
        return population.size();
    }

    public List<Individual> getPopulation() {
        return population;
    }

    public void setPopulation(List<Individual> population) {
        this.population = population;
    }

    public IFunction getGoalFunction() {
        return goalFunction;
    }

    public PopulationEvaluator getPopulationEvaluator() {
        return populationEvaluator;
    }

    public static int getSizeOfIndividual() {
        return SIZE_OF_INDIVIDUAL;
    }

    public static int getInitialPopulationSize() {
        return INITIAL_POPULATION_SIZE;
    }

    public static double getMinChromosomeValue() {
        return MIN_CHROMOSOME_VALUE;
    }

    public static double getMaxChromosomeValue() {
        return MAX_CHROMOSOME_VALUE;
    }

    public static double getMutationProbability() {
        return MUTATION_PROBABILITY;
    }

    public static int getMutationRange() {
        return MUTATION_RANGE;
    }
}
