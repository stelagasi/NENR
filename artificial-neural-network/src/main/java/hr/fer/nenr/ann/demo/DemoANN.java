package hr.fer.nenr.ann.demo;

import hr.fer.nenr.ann.ArtificialNeuralNetwork;
import hr.fer.nenr.ann.Example;
import hr.fer.nenr.ann.Point;

import java.util.List;

public class DemoANN {
    public static void main(String[] args) {
        ArtificialNeuralNetwork ann = new ArtificialNeuralNetwork(List.of(2,3,2));
        ann.train(List.of(new Example(List.of(new Point(1, 0.1)), List.of(1.0, 0.0))), 1);
        System.out.println(ann.predict(List.of(new Example(List.of(new Point(1, 0.1)), List.of()))));
    }
}
