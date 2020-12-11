package hr.fer.nenr.ann;

import java.util.Objects;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double calculateDistance(Point p1, Point p2) {
        return sqrt(pow(p1.getX() - p2.getX(), 2) + pow(p1.getY() - p2.getY(), 2));
    }

    public void add(Point point) {
        this.x += point.getX();
        this.y += point.getY();
    }

    public void subtract(Point point) {
        this.x -= point.getX();
        this.y -= point.getY();
    }

    public void divide(double value) {
        this.x /= value;
        this.y /= value;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 &&
                Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
