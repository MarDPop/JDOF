package main.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Recorder {
    
    private File csvOutputFile; 

    public ArrayList<double[]> data = new ArrayList<>();

    public Recorder(String filename) {
        this.csvOutputFile = new File(filename);
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
          .map(this::escapeSpecialCharacters)
          .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public void output(List<String[]> dataLines) throws IOException {
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
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