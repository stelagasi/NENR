package hr.fer.nenr.geneticalgorithm;

public class InputData {
    private final double x;
    private final double y;
    private final double result;

    public InputData(double x, double y, double result) {
        this.x = x;
        this.y = y;
        this.result = result;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getResult() {
        return result;
    }
}
