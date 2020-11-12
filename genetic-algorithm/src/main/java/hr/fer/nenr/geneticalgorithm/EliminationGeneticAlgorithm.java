package hr.fer.nenr.geneticalgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EliminationGeneticAlgorithm extends GeneticAlgorithm {
    private static final double MORTALITY = 0.5;
    private static final int NUMBER_OF_INDIVIDUALS_TO_SELECT = 3;

    public EliminationGeneticAlgorithm(IFunction goalFunction, PopulationEvaluator populationEvaluator) {
        super(goalFunction, populationEvaluator);
    }

    @Override
    public List<Individual> execute(int numberOfIterations) {
        this.setPopulation(generateStartingPopulation());
        PopulationEvaluator populationEvaluator = this.getPopulationEvaluator();
        int neededIndividuals = (int) (MORTALITY * getNumberInPopulation());

        for (int i = 0; i < numberOfIterations; i++) {
            double populationPenalty = populationEvaluator.evaluatePenalty(this.getPopulation(), this.getGoalFunction());
            System.out.println(i + " " + populationEvaluator.getBestIndividual());

            for (int j = 0; j < neededIndividuals; j++) {
                List<Individual> triple = selection(2, populationPenalty);
                Individual worstOfThree = findWorst(triple);
                triple.remove(worstOfThree);
                Individual child = reproduction(triple);
                mutation(List.of(child));
                if(populationEvaluator.evaluatePenaltyOfIndividual(child, getGoalFunction()) < worstOfThree.getPenalty()){
                    getPopulation().remove(worstOfThree);
                    getPopulation().add(child);
                }
            }
        }
        return this.getPopulation();
    }

    @Override
    List<Individual> selection(int parentsNeeded, double populationFitness) {
        List<Individual> triple = new ArrayList<>(NUMBER_OF_INDIVIDUALS_TO_SELECT);
        for (int j = 0; j < NUMBER_OF_INDIVIDUALS_TO_SELECT; j++) {
            triple.add(getPopulation().get(chooseIndividual()));
        }
        return triple;
    }

    private int chooseIndividual() {
        Random random = new Random();
        return random.nextInt(getNumberInPopulation());
    }

    private Individual findWorst(List<Individual> triple) {
        Individual worstOfThree = triple.get(0);
        for (int k = 1; k < NUMBER_OF_INDIVIDUALS_TO_SELECT; k++) {
            Individual individual = triple.get(k);
            if (individual.getPenalty() > worstOfThree.getPenalty()) {
                worstOfThree = individual;
            }
        }
        return worstOfThree;
    }
}
