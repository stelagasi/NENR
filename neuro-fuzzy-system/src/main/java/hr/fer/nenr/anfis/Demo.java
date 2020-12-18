package hr.fer.nenr.anfis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Math.*;
import static java.lang.Math.exp;

public class Demo {
    public static void main(String[] args) {
        double[][] examples = makeExamples();

        Anfis anfis = new Anfis(10);
        anfis.train(examples, 10000, 0.001, 0.001);
   }

    private static double[][] makeExamples() {
        double[][] examples = new double[81][3];

        for (int i = 0; i < examples.length; ) {
            for (int x = -4; x <= 4; x++) {
                for (int y = -4; y <= 4; y++) {
                    examples[i][0] = x;
                    examples[i][1] = y;
                    examples[i++][2] = (pow(x - 1, 2) + pow(y + 2, 2) - 5 * x * y + 3) * pow(cos((float) x / 5), 2);
                }
            }
        }

        return examples;
    }

    private static void writeStringBuilderInFile(File file, StringBuilder stringBuilder){
        try {
            FileWriter filewriter = new FileWriter(file);
            filewriter.write(stringBuilder.toString());
            filewriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeExamplesInFile2(File file, double[][] examples){
        StringBuilder stringBuilder = new StringBuilder();

        for (double[] example : examples) {
            for (double v : example) {
                stringBuilder.append(v).append(" ");
            }
            stringBuilder.append(System.lineSeparator());
        }

        try {
            FileWriter filewriter = new FileWriter(file);
            filewriter.write(stringBuilder.toString());
            filewriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void writeExamplesInFile(File file, double[][] examples) {
        StringBuilder stringBuilder = new StringBuilder();

        for(double[] example : examples){
            stringBuilder.append(example[0]).append(" ").append(example[1]).append(' ').append(example[2]).append(System.lineSeparator());
        }

        try {
            FileWriter filewriter = new FileWriter(file);
            filewriter.write(stringBuilder.toString());
            filewriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double[][] getPredictions(Anfis anfis){
        double[][] predictions = new double[81][3];
        for (int i = 0; i < 81; ) {
            for (int x = -4; x <= 4; x++) {
                for (int y = -4; y <= 4; y++) {
                    predictions[i][0] = x;
                    predictions[i][1] = y;
                    predictions[i++][2] = anfis.predict(new double[]{x, y});
                }
            }
        }

        return predictions;
    }

    private static double[][] getErrors(Anfis anfis, double[][] examples){
        double[][] errors = new double[81][3];

        for(int i = 0; i < examples.length; i++){
            errors[i][0] = examples[i][0];
            errors[i][1] = examples[i][1];
            errors[i][2] = anfis.predict(examples[i]) - examples[i][2];
        }

        return errors;
    }

    private static double[][] getNeuronOutput(Anfis anfis, int numberOfRule){
        double[][] result = new double[81][3];

        double a = anfis.getA()[numberOfRule];
        double b = anfis.getB()[numberOfRule];
        double c = anfis.getC()[numberOfRule];
        double d = anfis.getD()[numberOfRule];

        int index = 0;
        for (double i = -4; i <= 4.01; i += 0.1) {
            result[index][0] = i;
            result[index][1] = 1 / (1 + exp(b * (i - a)));
            result[index][2] = 1 / (1 + exp(d * (i - c)));
            index++;
        }

        return result;
    }
}
