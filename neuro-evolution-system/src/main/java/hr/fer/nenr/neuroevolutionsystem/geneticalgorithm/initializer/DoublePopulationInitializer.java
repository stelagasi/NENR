package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.initializer;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.DoubleIndividual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DoublePopulationInitializer extends PopulationInitializer<DoubleIndividual> {
    private final double lowerBound;
    private final double upperBound;

    public DoublePopulationInitializer(int populationSize, int numberOfChromosomes, double lowerBound, double upperBound) {
        super(populationSize, numberOfChromosomes);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public List<DoubleIndividual> initialize() {
        List<DoubleIndividual> startingPopulation = new ArrayList<>(getPopulationSize());
        Random random = new Random();

        for (int i = 0; i < getPopulationSize(); i++) {
            List<Double> chromosomes = new ArrayList<>(getNumberOfChromosomes());
            for (int j = 0; j < getNumberOfChromosomes(); j++) {
                chromosomes.add(random.nextDouble() * (upperBound - lowerBound) + lowerBound);
            }
            startingPopulation.add(new DoubleIndividual(chromosomes));
        }

        return startingPopulation;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }
}
