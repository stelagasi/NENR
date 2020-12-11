package hr.fer.nenr.ann;

import java.util.List;

public class Example {
    private final List<Point> points;
    private final List<Double> expectedLabel;

    public Example(List<Point> points, List<Double> expectedLabel) {
        this.points = points;
        this.expectedLabel = expectedLabel;
    }

    public List<Point> getPoints() {
        return points;
    }

    public List<Double> getExpectedLabel() {
        return expectedLabel;
    }

    @Override
    public String toString() {
        return "Example{" +
                "points=" + points +
                ", expectedLabel=" + expectedLabel +
                '}';
    }
}
