package hr.fer.nenr.neuroevolutionsystem;

import java.util.Arrays;

public class Sample {
    private final double[] x;
    private final double[] label;

    public Sample(double[] sample) {
        x = new double[sample.length-3];
        label = new double[3];

        for (int i = 0; i < x.length; i++) {
            x[i] = sample[i];
        }

        int labelIndex = 0;
        for (int i = x.length; i < sample.length; i++){
            label[labelIndex++] = sample[i];
        }
    }

    public double[] getX() {
        return x;
    }

    public double[] getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "x=" + Arrays.toString(x) +
                ", label=" + Arrays.toString(label) +
                '}';
    }
}
