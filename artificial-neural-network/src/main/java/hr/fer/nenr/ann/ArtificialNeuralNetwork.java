package hr.fer.nenr.ann;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static java.lang.Math.*;

public class ArtificialNeuralNetwork {
    private static final double LEARNING_RATE = 0.3;
    private static final double EPSILON = 0.001;
    private static final int NUMBER_OF_ITERATIONS = 10000;
    private final List<Integer> layers;
    private final List<List<List<Double>>> w;
    private final List<List<Double>> w0;
    private final List<List<List<Double>>> y;
    private final List<List<List<Double>>> gamma;
    private final Function<Double, Double> activationFunction = x -> 1 / (1 + exp(-x));

    public ArtificialNeuralNetwork(List<Integer> layers) {
        this.layers = layers;

        w0 = new ArrayList<>();
        for (int i = 1; i < layers.size(); i++) {
            List<Double> w0InLayer = new ArrayList<>();
            for (int j = 0; j < layers.get(i); j++) {
                //w0InLayer.add(0.2);
                w0InLayer.add(random() * 0.8 - 0.4);
            }
            w0.add(w0InLayer);
        }
        //double n = 0.0;
        w = new ArrayList<>();
        for (int i = 0; i < layers.size() - 1; i++) {
            List<List<Double>> weightsBetweenTwoLayers = new ArrayList<>();
            for (int j = 0; j < layers.get(i); j++) {
                List<Double> weightRow = new ArrayList<>();
                for (int k = 0; k < layers.get(i + 1); k++) {
                    weightRow.add(random() * 0.8 - 0.4);
                    //n += 0.1;
                   // weightRow.add(n);
                }
                weightsBetweenTwoLayers.add(weightRow);
            }
            w.add(weightsBetweenTwoLayers);
        }
        y = new ArrayList<>();
        gamma = new ArrayList<>();
    }

    public void train(List<Example> examples, int batchSize) {
        int numberOfIterations = NUMBER_OF_ITERATIONS;
        int currentExample = 0;
        do {
            y.clear();
            gamma.clear();

            for (int i = currentExample; i < currentExample+batchSize; i++) {
                List<List<Double>> exampleY = new ArrayList<>();
                List<Point> examplePoints = examples.get(i).getPoints();
                List<Double> yInFirstLayer = new ArrayList<>();
                for (Point examplePoint : examplePoints) {
                    yInFirstLayer.add(examplePoint.getX());
                    yInFirstLayer.add(examplePoint.getY());
                }
                exampleY.add(yInFirstLayer); //dodaj y prvog sloja
                for (int j = 1; j < layers.size(); j++) { // za preostale slojeve
                    List<Double> exampleYInLayer = new ArrayList<>();
                    for (int k = 0; k < layers.get(j); k++) { //za neuron u sloju
                        double y = w0.get(j - 1).get(k);
                        for (int l = 0; l < layers.get(j - 1); l++) { //za neuron prošlog sloja
                            y += exampleY.get(exampleY.size() - 1).get(l) * w.get(j - 1).get(l).get(k);
                        }
                        exampleYInLayer.add(activationFunction.apply(y));
                    }
                    exampleY.add(exampleYInLayer);
                }
                y.add(exampleY);

                List<List<Double>> exampleGamma = new ArrayList<>();

                List<Double> gammaInLastLayer = new ArrayList<>();
                List<Double> ys = exampleY.get(exampleY.size() - 1);
                for (int j = 0; j < ys.size(); j++) {
                    gammaInLastLayer.add(ys.get(j) * (1 - ys.get(j)) * (examples.get(i).getExpectedLabel().get(j) - ys.get(j)));
                }
                exampleGamma.add(gammaInLastLayer);

                for (int j = layers.size() - 2; j > 0; j--) { //za preostale slojeve
                    List<Double> gammaInLayer = new ArrayList<>();
                    for (int k = 0; k < layers.get(j); k++) { //za neuron u trenutnom sloju
                        double sum = 0;
                        for (int l = 0; l < layers.get(j + 1); l++) { //za neuron u sljedećem sloju
                            sum += w.get(j).get(k).get(l) * exampleGamma.get(exampleGamma.size() - 1).get(l);
                        }
                        gammaInLayer.add(sum * exampleY.get(j).get(k) * (1 - exampleY.get(j).get(k)));
                    }
                    exampleGamma.add(gammaInLayer);
                }
                Collections.reverse(exampleGamma);
                gamma.add(exampleGamma);
            }

            for (int i = 0; i < layers.size() - 1; i++) { //za svaki međusloj -> k
                for (int j = 0; j < layers.get(i); j++) { //za svaki neuron u trenutnom sloju -> i
                    for (int k = 0; k < layers.get(i + 1); k++) { //za svaki neuron u sljedećem sloju -> j
                        double sum = 0;
                        for (int l = 0; l < examples.size(); l++) { //po svakom primjeru
                            sum += gamma.get(l).get(i).get(k) * y.get(l).get(i).get(j);
                        }
                        w.get(i).get(j).set(k, w.get(i).get(j).get(k) + LEARNING_RATE * sum);
                    }
                }
            }

            for (int i = 1; i < layers.size() - 1; i++) { //za svaki sloj koji nije prvi ili zadnji
                for (int j = 0; j < layers.get(i); j++) { //za svaki neuron u trenutnom sloju
                    for (int k = 0; k < layers.get(i + 1); k++) { //za svaki neuron u sljedećem sloju
                        double sum = 0;
                        for (int l = 0; l < examples.size(); l++) { //po svakom primjeru
                            sum += gamma.get(l).get(i).get(k);
                        }
                        w0.get(i - 1).set(j, w0.get(i - 1).get(j) + LEARNING_RATE * sum);
                    }
                }
            }

            //prepravi w0 izlaznog sloja
            for (int i = 0; i < layers.get(layers.size() - 1); i++) { //za svaki neuron izlaznog sloja
                double sum = 0;
                for (int j = 0; j < examples.size(); j++) { //za svaki primjer
                    sum += gamma.get(j).get(gamma.get(j).size() - 1).get(i);
                }
                w0.get(w0.size() - 1).set(i, w0.get(w0.size() - 1).get(i) + LEARNING_RATE * sum);
            }
            currentExample += batchSize;
            if(currentExample + batchSize > examples.size()){
                currentExample = 0;
            }
            System.out.println(calculateSquaredError(examples));
            System.out.println(numberOfIterations);
        } while (calculateSquaredError(examples) > EPSILON && numberOfIterations-- > 0);
    }

    private double calculateSquaredError(List<Example> examples) {
        double squaredError = 0;
        for (int i = 0; i < examples.size(); i++) {
            List<Double> ys = predict(List.of(examples.get(i))).get(0);
            for (int j = 0; j < layers.get(layers.size() - 1); j++) {
                squaredError += pow(examples.get(i).getExpectedLabel().get(j) - ys.get(j), 2);
            }
        }
        return squaredError/examples.size();
    }

    public List<List<Double>> predict(List<Example> examples){
        List<List<Double>> predictions = new ArrayList<>(examples.size());

        for (int i = 0; i < examples.size(); i++) {
            List<List<Double>> exampleY = new ArrayList<>();
            List<Point> examplePoints = examples.get(i).getPoints();

            List<Double> yInFirstLayer = new ArrayList<>();
            for (int j = 0; j < examplePoints.size(); j++) {
                yInFirstLayer.add(examplePoints.get(i).getX());
                yInFirstLayer.add(examplePoints.get(i).getY());
            }
            exampleY.add(yInFirstLayer); //dodaj y prvog sloja

            for (int j = 1; j < layers.size(); j++) { // za preostale slojeve
                List<Double> exampleYInLayer = new ArrayList<>();
                for (int k = 0; k < layers.get(j); k++) { //za neuron u sloju
                    double y = w0.get(j - 1).get(k);
                    for (int l = 0; l < layers.get(j - 1); l++) { //za neuron prošlog sloja
                        y += exampleY.get(exampleY.size() - 1).get(l) * w.get(j - 1).get(l).get(k);
                    }
                    exampleYInLayer.add(activationFunction.apply(y));
                }
                exampleY.add(exampleYInLayer);
            }
            predictions.add(exampleY.get(exampleY.size()-1));
        }
        return predictions;
    }
}
