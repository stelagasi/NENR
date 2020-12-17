package hr.fer.nenr.ann.demo;

import hr.fer.nenr.ann.ArtificialNeuralNetwork;
import hr.fer.nenr.ann.PointHandler;
import hr.fer.nenr.ann.TestDrawer;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DemoRead {
    public static void main(String[] args) {
        int M = 20;
        File file = new File("E:\\FER\\NENR\\data2.txt");
        ArtificialNeuralNetwork ann = new ArtificialNeuralNetwork(List.of(40,8,5));
        ann.train(new ArrayList<>(PointHandler.readExamplesFromFile(file, M)), 20);
        SwingUtilities.invokeLater(() -> new TestDrawer(M, ann).setVisible(true));
    }
}
