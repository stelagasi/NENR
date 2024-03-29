package hr.fer.nenr.neuroevolutionsystem;

import hr.fer.nenr.neuroevolutionsystem.geneticalgorithm.individual.DoubleIndividual;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.lang.Math.*;

public class ArtificialNeuralNetwork {
    private final static Function<Double, Double> ACTIVATION_FUNCTION = x -> 1 / (1 + exp(-x));
    private final int[] layers;
    private final double[][] output;

    public ArtificialNeuralNetwork(int[] layers) {
        this.layers = layers;
        this.output = new double[layers.length-1][];

        for (int i = 0; i < output.length; i++) {
            output[i] = new double[layers[i+1]];
        }
    }

    public double[] calculateOutput(DoubleIndividual individual, Sample sample){
        List<Double> chromosomes = individual.getChromosomes();
        double[] x = sample.getX();

        int index = 0;

        for (int i = 0; i < layers[1]; i++) {
            double sum = 0;

            for (int j = 0; j < layers[0]; j++) {
                sum += abs(x[j] - chromosomes.get(index++)) / abs(chromosomes.get(index++));
            }

            output[0][i] = 1 / (1 + sum);
        }

        for (int layerIndex = 2; layerIndex < layers.length; layerIndex++) {
            for (int i = 0; i < layers[layerIndex]; i++) { //sadašnji sloj
                for (int j = 0; j < layers[layerIndex-1]; j++) { //prethodni sloj
                    output[layerIndex-1][i] += chromosomes.get(index++) * output[layerIndex-2][j];
                }
                output[layerIndex-1][i] += chromosomes.get(index++);
                output[layerIndex-1][i] = ACTIVATION_FUNCTION.apply(output[layerIndex-1][i]);
            }
        }

        return output[output.length-1];
    }

    public double calculateError(DoubleIndividual individual, Dataset dataset){
        List<Sample> samples = dataset.getSamples();

        double error = 0;

        for(Sample sample : samples){
            double[] expectedOutput = sample.getLabel();
            double[] output = calculateOutput(individual, sample);

            for (int i = 0; i < output.length; i++) {
                error += pow(expectedOutput[i] - output[i], 2);
            }
        }

        return error/samples.size();
    }

    public void testGA(DoubleIndividual individual, Dataset dataset){
        List<Sample> samples = dataset.getSamples();
        int numberOfCorrectSamples = 0;

        for (Sample sample : samples){
            double[] expectedOutput = sample.getLabel();
            double[] output = calculateOutput(individual, sample);

            for (int i = 0; i < output.length; i++) {
                if(output[i] < 0.5) output[i] = 0;
                else output[i] = 1;
            }

            boolean correct = Arrays.equals(expectedOutput, output);

            if(correct) numberOfCorrectSamples++;

            System.out.println(sample + ", myOutput = " + Arrays.toString(output) + " " + correct);
        }
        System.out.println("Correct classified: " + numberOfCorrectSamples);
        System.out.println("Incorrect classified: " + (samples.size() - numberOfCorrectSamples));
    }

    public int getNumberOfParameters(){
        int numberOfParameters = 2 * layers[0] * layers[1];

        for (int i = 2; i < layers.length; i++) {
            numberOfParameters += layers[i] * (layers[i-1] + 1);
        }

        return numberOfParameters;
    }

}
