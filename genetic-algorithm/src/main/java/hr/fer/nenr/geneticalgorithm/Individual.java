package hr.fer.nenr.geneticalgorithm;

import java.util.List;
import java.util.Objects;

public class Individual {
    private final List<Double> chromosomes;
    private double penalty;

    public Individual(List<Double> chromosomes) {
        this.chromosomes = chromosomes;
    }

    public List<Double> getChromosomes() {
        return chromosomes;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    @Override
    public String toString() {
        return "Individual{" +
                "chromosomes=" + chromosomes +
                ", penalty=" + penalty +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual that = (Individual) o;
        return Double.compare(that.penalty, penalty) == 0 &&
                Objects.equals(chromosomes, that.chromosomes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chromosomes, penalty);
    }
}
