package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.selectioner;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.DoubleIndividual;

import java.util.List;
import java.util.Random;

public class RouletteWheelSelectioner implements ISelectioner<DoubleIndividual> {
    private double populationFitness;

    @Override
    public DoubleIndividual select(List<DoubleIndividual> population) {
        Random random = new Random();
        int chosen = 0;
        double limit = random.nextDouble() * populationFitness;
        double upperLimit = population.get(0).getPenalty();
        while (limit > upperLimit && chosen < population.size() - 1) {
            upperLimit += population.get(++chosen).getPenalty();
        }
        return population.get(chosen);
    }

    public double getPopulationFitness() {
        return populationFitness;
    }

    public void setPopulationFitness(double populationFitness) {
        this.populationFitness = populationFitness;
    }
}
