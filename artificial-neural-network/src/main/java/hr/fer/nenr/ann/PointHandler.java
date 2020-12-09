package hr.fer.nenr.ann;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class PointHandler {

    private PointHandler() {
    }

    public static List<Point> findRepresentativePoints(List<Point> points, int M) {
        double sumX = 0, sumY = 0;

        for (Point point : points) {
            sumX += point.getX();
            sumY += point.getY();
        }

        Point meanPoint = new Point(sumX / points.size(), sumY / points.size());

        double mx = 0, my = 0;

        for (Point point : points) {
            point.subtract(meanPoint);
            if (abs(point.getX()) > mx) mx = point.getX();
            if (abs(point.getY()) > my) my = point.getY();
        }

        double m = max(mx, my);

        for (Point point : points) {
            point.divide(m);
        }

        List<Double> distances = calculateDistances(points);
        double D = distances.stream().mapToDouble(e -> e).sum();

        List<Point> representativePoints = new ArrayList<>(M);
        double currentDistance = 0;
        int lastIndex = 0;

        for (int i = 0; i < M; i++) {
            double wantedDistance = i * D / (M - 1);

            while(true){
                if(wantedDistance == currentDistance) {
                    representativePoints.add(points.get(lastIndex));
                    break;
                } else if (currentDistance < wantedDistance) {
                    currentDistance += distances.get(lastIndex++);
                } else {
                    representativePoints.add(points.get(lastIndex));
                    break;
                }
            }
        }

        return representativePoints;
    }

    private static List<Double> calculateDistances(List<Point> points){
        List<Double> distances = new ArrayList<>();
        for (int i = 1; i < points.size(); i++) {
            distances.add(Point.calculateDistance(points.get(i-1), points.get(i)));
        }
        return distances;
    }

}
