package hr.fer.nenr.ann;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static void writeExampleInFile(File file, Example example) {
        StringBuilder stringBuilder = new StringBuilder();

        List<Point> points = example.getPoints();
        for(Point point : points){
            stringBuilder.append(point.getX()).append(",").append(point.getY());
        }
        stringBuilder.append(",");
        List<Double> expectedLabel = example.getExpectedLabel();
        for (int i = 0; i < expectedLabel.size()-1; i++) {
            stringBuilder.append(expectedLabel.get(i)).append(",");
        }
        stringBuilder.append(expectedLabel.get(expectedLabel.size()-1));

        try {
            FileWriter filewriter = new FileWriter(file, true);
            filewriter.write(stringBuilder.toString() + System.lineSeparator());
            filewriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Example> readExamplesFromFile(File file, int M){
        List<Example> examples = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            examples.add(extractExample(line, M));
            while (true) {
                line = reader.readLine();
                if(line == null) break;
                examples.add(extractExample(line, M));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return examples;
    }

    private static Example extractExample(String line, int M){
        String[] splitLine = line.split(",");
        List<Double> numbers = Arrays.stream(splitLine).map(Double::parseDouble).collect(Collectors.toList());
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            points.add(new Point(numbers.get(i), numbers.get(i+1)));
        }
        List<Double> expectedLabel = new ArrayList<>();
        for (int i = 2*M; i < numbers.size(); i++) {
            expectedLabel.add(numbers.get(i));
        }
        return new Example(points, expectedLabel);
    }
}
