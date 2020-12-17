package hr.fer.nenr.ann.demo;

import hr.fer.nenr.ann.ArtificialNeuralNetwork;
import hr.fer.nenr.ann.Example;
import hr.fer.nenr.ann.Point;

import java.util.List;

public class Demo2 {
    public static void main(String[] args) {
        ArtificialNeuralNetwork ann = new ArtificialNeuralNetwork(List.of(2, 10, 2));
        ann.train(List.of(
                new Example(List.of(new Point(-1, -1)), List.of(1.0, 1.0)),
                new Example(List.of(new Point(-0.8, -0.8)), List.of(0.64, 0.64)),
                new Example(List.of(new Point(-0.6, -0.6)), List.of(0.36, 0.36)),
                new Example(List.of(new Point(-0.4, -0.4)), List.of(0.16, 0.16)),
                new Example(List.of(new Point(-0.2, -0.2)), List.of(0.04, 0.04)),
                new Example(List.of(new Point(0.0, 0.0)), List.of(0.0, 0.0)),
                new Example(List.of(new Point(0.2, 0.2)), List.of(0.04, 0.04)),
                new Example(List.of(new Point(0.4, 0.4)), List.of(0.16, 0.16)),
                new Example(List.of(new Point(0.6, 0.6)), List.of(0.36, 0.36)),
                new Example(List.of(new Point(0.8, 0.8)), List.of(0.64, 0.64)),
                new Example(List.of(new Point(1, 1)), List.of(1.0, 1.0))
        ), 1);

        for(double i = -1; i <= 1; i+=0.2){
            System.out.println(i);
            System.out.println(ann.predict(List.of(new Example(List.of(new Point(i, i)), List.of()))));
        }
    }
}
