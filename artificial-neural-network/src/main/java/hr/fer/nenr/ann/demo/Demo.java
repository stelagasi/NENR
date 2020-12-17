package hr.fer.nenr.ann.demo;

import hr.fer.nenr.ann.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Demo {
    private static final int NUMBER_OF_CLASSES = 5;

    public static void main(String[] args) throws IOException {
        if (args.length != 4) throw new IllegalArgumentException("Number of arguments given is not 4");
        File file = new File(args[0]);
        if(file.exists()) file.delete();
        file.createNewFile();
        int M = Integer.parseInt(args[1]);

        List<Integer> layers = Arrays.stream(args[2].split("x")).map(Integer::parseInt).collect(Collectors.toList());
        if (layers.get(0) != 2 * M) throw new IllegalArgumentException("First layer isn't 2*M");
        if (layers.get(layers.size() - 1) != NUMBER_OF_CLASSES)
            throw new IllegalArgumentException("Last layer should have " + NUMBER_OF_CLASSES + " neurons");
        ArtificialNeuralNetwork ann = new ArtificialNeuralNetwork(layers);
        GestureDrawer gestureDrawer = new GestureDrawer(M, file, ann, 10);
        SwingUtilities.invokeLater(() -> gestureDrawer.setVisible(true));
    }
}
