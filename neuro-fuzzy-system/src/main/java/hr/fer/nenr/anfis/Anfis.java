package hr.fer.nenr.anfis;

import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.exp;
import static java.lang.Math.pow;

public class Anfis {
    private static final double INITIAL_INTERVAL = 1;
    private final double[] a;
    private final double[] b;
    private final double[] c;
    private final double[] d;
    private final double[] p;
    private final double[] q;
    private final double[] r;
    private final double[] outputANeuron;
    private final double[] outputBNeuron;
    private final double[] w;
    private final double[] deltaA;
    private final double[] deltaB;
    private final double[] deltaC;
    private final double[] deltaD;
    private final double[] deltaP;
    private final double[] deltaQ;
    private final double[] deltaR;


    public Anfis(int numberOfRules) {
        a = new double[numberOfRules];
        b = new double[numberOfRules];
        c = new double[numberOfRules];
        d = new double[numberOfRules];

        p = new double[numberOfRules];
        q = new double[numberOfRules];
        r = new double[numberOfRules];

        outputANeuron = new double[numberOfRules];
        outputBNeuron = new double[numberOfRules];

        w = new double[numberOfRules];

        deltaA = new double[numberOfRules];
        deltaB = new double[numberOfRules];
        deltaC = new double[numberOfRules];
        deltaD = new double[numberOfRules];

        deltaP = new double[numberOfRules];
        deltaQ = new double[numberOfRules];
        deltaR = new double[numberOfRules];

        Random random = new Random();

        for (int i = 0; i < numberOfRules; i++) {
            a[i] = (INITIAL_INTERVAL + INITIAL_INTERVAL) * random.nextDouble() - INITIAL_INTERVAL;
            b[i] = (INITIAL_INTERVAL + INITIAL_INTERVAL) * random.nextDouble() - INITIAL_INTERVAL;
            c[i] = (INITIAL_INTERVAL + INITIAL_INTERVAL) * random.nextDouble() - INITIAL_INTERVAL;
            d[i] = (INITIAL_INTERVAL + INITIAL_INTERVAL) * random.nextDouble() - INITIAL_INTERVAL;

            p[i] = (INITIAL_INTERVAL + INITIAL_INTERVAL) * random.nextDouble() - INITIAL_INTERVAL;
            q[i] = (INITIAL_INTERVAL + INITIAL_INTERVAL) * random.nextDouble() - INITIAL_INTERVAL;
            r[i] = (INITIAL_INTERVAL + INITIAL_INTERVAL) * random.nextDouble() - INITIAL_INTERVAL;
        }
    }

    public void train(double[][] examples, int numberOfIterations, double eta1, double eta2) {
        for (int i = 0; i < numberOfIterations; i++) {
            for (int j = 0; j < deltaA.length; j++) {
                deltaA[j] = deltaB[j] = deltaC[j] = deltaD[j] = deltaP[j] = deltaQ[j] = deltaR[j] = 0;
            }

            for (double[] example : examples) {
                double x = example[0];
                double y = example[1];
                double fValue = example[2];

                double output = 0;
                double wSum = 0;

                for (int j = 0; j < outputANeuron.length; j++) {
                    outputANeuron[j] = 1 / (1 + exp(b[j] * (x - a[j])));
                    outputBNeuron[j] = 1 / (1 + exp(d[j] * (y - c[j])));
                    wSum += w[j] = outputANeuron[j] * outputBNeuron[j];
                    output += (p[j] * x + q[j] * y + r[j]) * w[j];
                }

                output /= wSum;
                double error = fValue - output;

                for (int j = 0; j < deltaP.length; j++) {
                    deltaP[j] += error * w[j] * x / wSum;
                    deltaQ[j] += error * w[j] * y / wSum;
                    deltaR[j] += error * w[j] / wSum;
                }

                for (int j = 0; j < deltaA.length; j++) {
                    double number = 0;
                    for (int k = 0; k < w.length; k++) {
                        if (j == k) continue;
                        number += w[k] * ((p[j] * x + q[j] * y + r[j]) - (p[k] * x + q[k] * y + r[k]));
                    }
                    double number2 = error * number / (wSum * wSum);
                    deltaA[j] += number2 * outputBNeuron[j] * b[j] * (1 - outputANeuron[j]) * outputANeuron[j];
                    deltaB[j] += number2 * outputBNeuron[j] * (a[j] - x) * (1 - outputANeuron[j]) * outputANeuron[j];
                    deltaC[j] += number2 * outputANeuron[j] * d[j] * (1 - outputBNeuron[j]) * outputBNeuron[j];
                    deltaD[j] += number2 * outputANeuron[j] * (c[j] - y) * (1 - outputBNeuron[j]) * outputBNeuron[j];
                }
            }
            for (int j = 0; j < deltaA.length; j++) {
                a[j] += eta1 * deltaA[j];
                b[j] += eta1 * deltaB[j];
                c[j] += eta1 * deltaC[j];
                d[j] += eta1 * deltaD[j];
                p[j] += eta2 * deltaP[j];
                q[j] += eta2 * deltaQ[j];
                r[j] += eta2 * deltaR[j];
                deltaA[j] = deltaB[j] = deltaC[j] = deltaD[j] = deltaP[j] = deltaQ[j] = deltaR[j] = 0;
            }
            System.out.println(i + " " + calculateError(examples));
        }
    }

    public void trainBatch(double[][] examples, int numberOfIterations, double eta1, double eta2, int batchSize) {
        for (int i = 0; i < numberOfIterations; i++) {
            for (int j = 0; j < deltaA.length; j++) {
                deltaA[j] = deltaB[j] = deltaC[j] = deltaD[j] = deltaP[j] = deltaQ[j] = deltaR[j] = 0;
            }

            double[][] batchGroup = new double[batchSize][3];
            Random random = new Random();
            for (int j = 0; j < batchSize; j++) {
                int index = random.nextInt(examples.length);
                batchGroup[j] = Arrays.copyOf(examples[index], examples[index].length);
            }

            for (double[] batch : batchGroup) {
                double x = batch[0];
                double y = batch[1];
                double fValue = batch[2];

                double output = 0;
                double wSum = 0;

                for (int j = 0; j < outputANeuron.length; j++) {
                    outputANeuron[j] = 1 / (1 + exp(b[j] * (x - a[j])));
                    outputBNeuron[j] = 1 / (1 + exp(d[j] * (y - c[j])));
                    wSum += w[j] = outputANeuron[j] * outputBNeuron[j];
                    output += (p[j] * x + q[j] * y + r[j]) * w[j];
                }

                output /= wSum;
                double error = fValue - output;

                for (int j = 0; j < deltaP.length; j++) {
                    deltaP[j] += error * w[j] * x / wSum;
                    deltaQ[j] += error * w[j] * y / wSum;
                    deltaR[j] += error * w[j] / wSum;
                }

                for (int j = 0; j < deltaA.length; j++) {
                    double number = 0;
                    for (int k = 0; k < w.length; k++) {
                        if (j == k) continue;
                        number += w[k] * ((p[j] * x + q[j] * y + r[j]) - (p[k] * x + q[k] * y + r[k]));
                    }
                    double number2 = error * number / (wSum * wSum);
                    deltaA[j] += number2 * outputBNeuron[j] * b[j] * (1 - outputANeuron[j]) * outputANeuron[j];
                    deltaB[j] += number2 * outputBNeuron[j] * (a[j] - x) * (1 - outputANeuron[j]) * outputANeuron[j];
                    deltaC[j] += number2 * outputANeuron[j] * d[j] * (1 - outputBNeuron[j]) * outputBNeuron[j];
                    deltaD[j] += number2 * outputANeuron[j] * (c[j] - y) * (1 - outputBNeuron[j]) * outputBNeuron[j];
                }
            }
            for (int j = 0; j < deltaA.length; j++) {
                a[j] += eta1 * deltaA[j];
                b[j] += eta1 * deltaB[j];
                c[j] += eta1 * deltaC[j];
                d[j] += eta1 * deltaD[j];
                p[j] += eta2 * deltaP[j];
                q[j] += eta2 * deltaQ[j];
                r[j] += eta2 * deltaR[j];
                deltaA[j] = deltaB[j] = deltaC[j] = deltaD[j] = deltaP[j] = deltaQ[j] = deltaR[j] = 0;
            }

            System.out.println(i + " " + calculateError(examples));

        }
    }

    private double calculateError(double[][] examples) {
        double error = 0.0;

        for (double[] example : examples) {
            double x = example[0];
            double y = example[1];
            double fValue = example[2];

            double output = 0;
            double wSum = 0;

            for (int j = 0; j < outputANeuron.length; j++) {
                outputANeuron[j] = 1 / (1 + exp(b[j] * (x - a[j])));
                outputBNeuron[j] = 1 / (1 + exp(d[j] * (y - c[j])));
                wSum += w[j] = outputANeuron[j] * outputBNeuron[j];
                output += (p[j] * x + q[j] * y + r[j]) * w[j];
            }

            output /= wSum;
            error += 0.5 * pow(fValue - output, 2);
        }
        return error / examples.length;
    }

    public double predict(double[] example) {
        double x = example[0];
        double y = example[1];

        double output = 0;
        double wSum = 0;

        for (int j = 0; j < outputANeuron.length; j++) {
            outputANeuron[j] = 1 / (1 + exp(b[j] * (x - a[j])));
            outputBNeuron[j] = 1 / (1 + exp(d[j] * (y - c[j])));
            wSum += w[j] = outputANeuron[j] * outputBNeuron[j];
            output += (p[j] * x + q[j] * y + r[j]) * w[j];
        }

        return output /= wSum;
    }

    public double[] getA() {
        return a;
    }

    public double[] getB() {
        return b;
    }

    public double[] getC() {
        return c;
    }

    public double[] getD() {
        return d;
    }
}
