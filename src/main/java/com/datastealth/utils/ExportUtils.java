package com.datastealth.utils;

import com.datastealth.model.AnnotationScanResult;
import com.datastealth.model.AnnotationScanResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportUtils {
    // Export the result in JSON and CSV formats. Also, the summary is generated using summary.txt.
    public static void exportToJson(String path, List<AnnotationScanResult> results) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new FileWriter(path), results);
        } catch (IOException e) {
            System.err.println("Failed to export JSON: " + e.getMessage());
        }
    }

    public static void exportToCsv(String path, List<AnnotationScanResult> results) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write("Class,Type,Name,Annotations\n");
            for (AnnotationScanResult r : results) {
                for (String annotation : r.getClassAnnotations())
                    writer.write(r.getClassName() + ",Class,," + annotation + "\n");
                for (var entry : r.getMethodAnnotations().entrySet())
                    for (String annotation : entry.getValue())
                        writer.write(r.getClassName() + ",Method," + entry.getKey() + "," + annotation + "\n");
                for (var entry : r.getFieldAnnotations().entrySet())
                    for (String annotation : entry.getValue())
                        writer.write(r.getClassName() + ",Field," + entry.getKey() + "," + annotation + "\n");
            }
        } catch (IOException e) {
            System.err.println("Failed to export CSV: " + e.getMessage());
        }
    }

    public static void exportSummary(String path, AnnotationScanResponse response) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write("Class Count: " + response.getTotalClasses() + "\n");
            writer.write("Class Annotations: " + response.getTotalClassAnnotations() + "\n");
            writer.write("Method Annotations: " + response.getTotalMethodAnnotations() + "\n");
            writer.write("Field Annotations: " + response.getTotalFieldAnnotations() + "\n");
            writer.write("Message:" + response.getMessage() + "\n");
        } catch (IOException e) {
            System.err.println("Failed to write summary: " + e.getMessage());
        }
    }
}
