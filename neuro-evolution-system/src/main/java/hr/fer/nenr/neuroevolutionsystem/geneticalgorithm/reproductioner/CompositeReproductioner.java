package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.reproductioner;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.DoubleIndividual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CompositeReproductioner implements IReproductioner<DoubleIndividual>{
    private final List<IReproductioner<DoubleIndividual>> reproductioners;
    private final List<Double> probabilities;

    public CompositeReproductioner(List<IReproductioner<DoubleIndividual>> reproductioners, List<Double> probabilities) {
        this.reproductioners = reproductioners;
        this.probabilities = probabilities;
    }


    @Override
    public DoubleIndividual reproduce(DoubleIndividual firstParent, DoubleIndividual secondParent) {
        return reproductioners.get(chooseReproductioner()).reproduce(firstParent, secondParent);
    }

    @Override
    public List<DoubleIndividual> reproduceMultiple(List<DoubleIndividual> parents) {
        List<DoubleIndividual> children = new ArrayList<>();
        for (int i = 0; i < parents.size(); i += 2) {
            children.add(reproduce(parents.get(i), parents.get(i + 1)));
        }
        return children;
    }

    private int chooseReproductioner(){
        int chosen = 0;
        double limit = new Random().nextDouble() * probabilities.stream().mapToDouble(e -> e).sum();
        double upperLimit = probabilities.get(0);

        while (limit > upperLimit && chosen < probabilities.size() - 1) {
            upperLimit += probabilities.get(++chosen);
        }

        return chosen;
    }
}
