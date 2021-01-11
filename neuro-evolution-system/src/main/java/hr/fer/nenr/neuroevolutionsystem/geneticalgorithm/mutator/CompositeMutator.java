package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.mutator;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.DoubleIndividual;

import java.util.List;
import java.util.Random;

public class CompositeMutator extends Mutator<DoubleIndividual> {
    private final List<IMutator<DoubleIndividual>> mutators;
    private final List<Double> probabilities;

    public CompositeMutator(List<IMutator<DoubleIndividual>> mutators, List<Double> probabilities) {
        this.mutators = mutators;
        this.probabilities = probabilities;
    }

    @Override
    public void mutate(DoubleIndividual child) {
        mutators.get(chooseMutator()).mutate(child);
    }

    private int chooseMutator(){
        int chosen = 0;
        double limit = new Random().nextDouble() * probabilities.stream().mapToDouble(e -> e).sum();
        double upperLimit = probabilities.get(0);

        while (limit > upperLimit && chosen < probabilities.size() - 1) {
            upperLimit += probabilities.get(++chosen);
        }

        return chosen;
    }
}
