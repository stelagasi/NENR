package hr.fer.nenr.geneticalgorithm.demo;

import hr.fer.nenr.geneticalgorithm.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> dataset1 = Files.readAllLines(Path.of("E:\\FER\\NENR\\Datasets\\zad4-dataset1.txt"));
        List<String> dataset2 = Files.readAllLines(Path.of("E:\\FER\\NENR\\Datasets\\zad4-dataset2.txt"));

        List<InputData> preparedData1 = prepareDataset(dataset1);
        List<InputData> preparedData2 = prepareDataset(dataset2);

        IFunction function = (x, y, chromosomes) -> sin(chromosomes.get(0) + chromosomes.get(1) * x) +
                chromosomes.get(2) * cos(x * (chromosomes.get(3) + y)) * (1 / (1 + exp(pow(x - chromosomes.get(4), 2))));

        PopulationEvaluator populationEvaluator = new PopulationEvaluator(preparedData1);
        GeneticAlgorithm gga = new GenerationalGeneticAlgorithm(function, populationEvaluator, true);
        gga.execute(1000);
//        GeneticAlgorithm ega = new EliminationGeneticAlgorithm(function, populationEvaluator);
//        ega.execute(1000);
    }

    private static List<InputData> prepareDataset(List<String> dataset) {
        List<InputData> inputDataset = new ArrayList<>();
        for (String line : dataset) {
            String[] data = line.split("\t");
            inputDataset.add(new InputData(Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2])));
        }
        return inputDataset;
    }
}
