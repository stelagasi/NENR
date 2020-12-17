package hr.fer.nenr.anfis;

import java.util.Arrays;

import static java.lang.Math.*;

public class Demo {
    public static void main(String[] args) {
        Anfis anfis = new Anfis(10);
        anfis.train(makeExamples(), 100000, 0.001, 0.001);

    }

    private static double[][] makeExamples() {
        double[][] examples = new double[81][3];

        for (int i = 0; i < examples.length;) {
            for (int x = -4; x <= 4; x++) {
                for (int y = -4; y <= 4; y++) {
                    examples[i][0] = x;
                    examples[i][1] = y;
                    examples[i++][2] = (pow(x - 1, 2) + pow(y + 2, 2) - 5 * x * y + 3) * pow(cos((float)x / 5), 2);
                }
            }
        }

        return examples;
    }
}
