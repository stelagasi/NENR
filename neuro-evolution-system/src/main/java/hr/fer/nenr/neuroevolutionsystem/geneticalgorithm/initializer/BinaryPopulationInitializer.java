package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.initializer;


import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.BinaryIndividual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.*;

public class BinaryPopulationInitializer extends PopulationInitializer<BinaryIndividual> {
    private final int numberOfBitsInChromosome;

    public BinaryPopulationInitializer(int populationSize, int numberOfChromosomes, int precision, int lowerBound, int upperBound) {
        super(populationSize, numberOfChromosomes);
        this.numberOfBitsInChromosome = (int) ceil(log10((upperBound - lowerBound) * pow(10, precision) + 1) / log10(2));
    }

    @Override
    public List<BinaryIndividual> initialize() {
        List<BinaryIndividual> startingPopulation = new ArrayList<>(getPopulationSize());

        for (int i = 0; i < getPopulationSize(); i++) {
            List<List<Boolean>> chromosomes = new ArrayList<>(getNumberOfChromosomes());
            for (int j = 0; j < getNumberOfChromosomes(); j++) {
                chromosomes.add(initializeChromosome());
            }
            startingPopulation.add(new BinaryIndividual(chromosomes));
        }

        return startingPopulation;
    }

    private List<Boolean> initializeChromosome() {
        List<Boolean> chromosome = new ArrayList<>(numberOfBitsInChromosome);
        Random random = new Random();

        for (int i = 0; i < numberOfBitsInChromosome; i++) {
            chromosome.add(random.nextBoolean());
        }

        return chromosome;
    }
}
