package hr.fer.nenr.geneticalgorithm;

import java.util.List;
import java.util.Objects;

public class Individual {
    private final List<Double> chromosomes;
    private double fitness;

    public Individual(List<Double> chromosomes) {
        this.chromosomes = chromosomes;
    }

    public List<Double> getChromosomes() {
        return chromosomes;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        return "Individual{" +
                "chromosomes=" + chromosomes +
                ", fitness=" + fitness +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual that = (Individual) o;
        return Double.compare(that.fitness, fitness) == 0 &&
                Objects.equals(chromosomes, that.chromosomes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chromosomes, fitness);
    }
}
