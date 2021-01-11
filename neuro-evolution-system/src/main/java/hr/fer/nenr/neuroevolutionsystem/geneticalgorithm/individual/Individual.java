package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual;

import java.util.List;

public class Individual<T> implements IIndividual<T> {
    protected final List<T> chromosomes;
    protected double penalty;

    public Individual(List<T> chromosomes) {
        this.chromosomes = chromosomes;
    }

    @Override
    public List<T> getChromosomes() {
        return chromosomes;
    }

    @Override
    public double getPenalty() {
        return penalty;
    }

    @Override
    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }
}
