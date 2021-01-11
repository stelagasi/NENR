package hr.fer.nenr.neuroevolutionsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dataset {
    private final List<Sample> samples;

    public Dataset(File file) {
        samples = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                samples.add(new Sample(Arrays.stream(line.split("\\s+")).mapToDouble(Double::parseDouble).toArray()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNumberOfSamples(){ return samples.size(); }

    public List<Sample> getSamples() { return samples; }
}
