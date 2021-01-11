package hr.fer.nenr.neuroevolutionsystem.demo;

import hr.fer.nenr.neuroevolutionsystem.Dataset;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Dataset dataset = new Dataset(new File("C:\\FER\\NENR\\neuro-evolution-system\\zad7-dataset.txt"));
        System.out.println(dataset.get(0));
    }
}
