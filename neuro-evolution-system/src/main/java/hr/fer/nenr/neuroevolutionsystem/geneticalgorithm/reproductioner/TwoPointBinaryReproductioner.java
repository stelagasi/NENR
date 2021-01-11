package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.reproductioner;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.BinaryIndividual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TwoPointBinaryReproductioner implements IReproductioner<BinaryIndividual> {
    @Override
    public BinaryIndividual reproduce(BinaryIndividual firstParent, BinaryIndividual secondParent) {
        Random random = new Random();
        int firstCrossoverPoint = random.nextInt(firstParent.getChromosomes().size() / 2);
        int secondCrossoverPoint = random.nextInt(firstParent.getChromosomes().size());

        while (firstCrossoverPoint >= secondCrossoverPoint) {
            secondCrossoverPoint = random.nextInt(firstParent.getChromosomes().size());
        }

        List<List<Boolean>> childChromosomes = new ArrayList<>();
        for (int i = 0; i < firstCrossoverPoint; i++) {
            childChromosomes.add(new ArrayList<>(firstParent.getChromosomes().get(i)));
        }
        for (int i = firstCrossoverPoint; i < secondCrossoverPoint; i++) {
            childChromosomes.add(new ArrayList<>(secondParent.getChromosomes().get(i)));
        }
        for (int i = secondCrossoverPoint; i < firstParent.getChromosomes().size(); i++) {
            childChromosomes.add(new ArrayList<>(firstParent.getChromosomes().get(i)));
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
