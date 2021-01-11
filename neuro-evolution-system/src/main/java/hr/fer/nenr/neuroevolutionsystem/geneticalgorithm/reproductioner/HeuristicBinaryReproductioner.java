package hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.reproductioner;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.BinaryIndividual;

import java.util.ArrayList;
import java.util.List;

public class HeuristicBinaryReproductioner implements IReproductioner<BinaryIndividual> {
    @Override
    public BinaryIndividual reproduce(BinaryIndividual firstParent, BinaryIndividual secondParent) {
        BinaryIndividual first = firstParent;
        BinaryIndividual second = secondParent;

        if (firstParent.getPenalty() > secondParent.getPenalty()) {
            first = secondParent;
            second = firstParent;
        }

        List<List<Boolean>> childChromosomes = new ArrayList<>();
        for (int i = 0; i < firstParent.getChromosomes().size(); i++) {
            List<Boolean> chromosome = new ArrayList<>();
            childChromosomes.add(chromosome);
            for (int j = 0; j < firstParent.getChromosomes().get(0).size(); j++) {
                childChromosomes.get(i).add(Math.random() < 0.75 ? first.getChromosomes().get(i).get(j) : second.getChromosomes().get(i).get(j));
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