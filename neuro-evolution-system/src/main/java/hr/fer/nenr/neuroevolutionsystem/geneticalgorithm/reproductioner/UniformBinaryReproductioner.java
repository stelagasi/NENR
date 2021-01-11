package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.reproductioner;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.BinaryIndividual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UniformBinaryReproductioner implements IReproductioner<BinaryIndividual> {
    @Override
    public BinaryIndividual reproduce(BinaryIndividual firstParent, BinaryIndividual secondParent) {
        Random random = new Random();

        List<List<Boolean>> childChromosomes = new ArrayList<>();

        for (int i = 0; i < firstParent.getChromosomes().size(); i++) {
            if (random.nextBoolean()) {
                childChromosomes.add(new ArrayList<>(firstParent.getChromosomes().get(i)));
            } else {
                childChromosomes.add(new ArrayList<>(secondParent.getChromosomes().get(i)));
            }
        }
        return new BinaryIndividual(childChromosomes);
    }

    @Override
    public List<BinaryIndividual> reproduceMultiple(List<BinaryIndividual> parents) {
        List<BinaryIndividual> children = new ArrayList<>();
        for (int i = 0; i < parents.size(); i += 2) {
            children.add(reproduce(parents.get(i), parents.get(i + 1)));
        }
        return children;
    }
}
