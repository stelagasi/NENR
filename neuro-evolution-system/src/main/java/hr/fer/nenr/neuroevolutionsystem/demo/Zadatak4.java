package hr.fer.nenr.neuroevolutionsystem.demo;

import hr.fer.nenr.neuroevolutionsystem.ArtificialNeuralNetwork;
import hr.fer.nenr.neuroevolutionsystem.Dataset;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.EliminationGeneticAlgorithm;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.evaluator.DoublePopulationEvaluator;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.DoubleIndividual;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.initializer.DoublePopulationInitializer;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.mutator.CompositeMutator;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.mutator.GaussianDoubleMutator;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.mutator.GaussianReplaceDoubleMutator;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.reproductioner.CompositeReproductioner;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.reproductioner.DoubleReproductioner;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.reproductioner.SimulatedBinaryDoubleReproductioner;
import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.selectioner.RandomSelectioner;

import java.io.File;
import java.util.List;

public class Zadatak4 {
    public static void main(String[] args) {
        Dataset dataset = new Dataset(new File("C:\\FER\\NENR\\neuro-evolution-system\\zad7-dataset.txt"));

        ArtificialNeuralNetwork ann = new ArtificialNeuralNetwork(new int[]{2, 8, 3});
        EliminationGeneticAlgorithm<DoubleIndividual> ega = new EliminationGeneticAlgorithm<>(new DoublePopulationInitializer(200, ann.getNumberOfParameters(), 0, 1), new CompositeMutator(List.of(new GaussianDoubleMutator(0.01, 0.4), new GaussianDoubleMutator(0.01, 0.6), new GaussianReplaceDoubleMutator(0.01, 0.5)), List.of(1.0, 1.0, 1.0)), new CompositeReproductioner(List.of(new DoubleReproductioner(), new SimulatedBinaryDoubleReproductioner()), List.of(2.0, 1.0)), new RandomSelectioner<>(), new DoublePopulationEvaluator(ann, dataset), 3);
        System.out.println(ega.execute(1_000_000, 1e-7));
        ega.test();
    }
}
