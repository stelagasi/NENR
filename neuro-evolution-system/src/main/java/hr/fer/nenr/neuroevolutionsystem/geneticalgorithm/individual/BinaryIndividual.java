package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual;

import java.util.List;
import java.util.Objects;

public class BinaryIndividual extends Individual<List<Boolean>> {

    public BinaryIndividual(List<List<Boolean>> chromosomes) {
        super(chromosomes);
    }

    @Override
    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    @Override
    public String toString() {
        return "BinaryIndividual{" +
                "chromosomes=" + chromosomes +
                ", penalty=" + penalty +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryIndividual that = (BinaryIndividual) o;
        return Double.compare(that.penalty, penalty) == 0 &&
                Objects.equals(chromosomes, that.chromosomes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chromosomes, penalty);
    }
}
