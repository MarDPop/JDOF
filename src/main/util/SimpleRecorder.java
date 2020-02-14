package main.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleRecorder {
    
    private File csvOutputFile; 

    public ArrayList<double[]> data = new ArrayList<>();

    public String[] header;

    public SimpleRecorder(String filename) {
        this.csvOutputFile = new File(filename);
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
          .collect(Collectors.joining(","));
    }

    public void output(List<String[]> dataLines) throws IOException {
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println(convertToCSV(header));
            dataLines.stream()
              .map(this::convertToCSV)
              .forEach(pw::println);
        }
    }

    public void output() {
        List<String[]> dataConverted = new ArrayList<>();
        for(double[] row : this.data) {
            String[] rowConverted = new String[row.length];
            for(int i = 0; i < row.length; i++) {
                rowConverted[i] = Double.toString(row[i]);
            }
            dataConverted.add(rowConverted);
        }
        try {
            output(dataConverted);
        } catch (IOException e) {
            e.getMessage();
        }
    }

}