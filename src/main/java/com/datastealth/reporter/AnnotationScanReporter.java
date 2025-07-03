package com.datastealth.reporter;

import com.datastealth.model.AnnotationScanResponse;
import com.datastealth.utils.ExportUtils;

import java.io.File;

public class AnnotationScanReporter {
    public static void printReport(AnnotationScanResponse response) {
        System.out.println("\n--- Summary ---");
        System.out.println("Total Classes: " + response.getTotalClasses());
        System.out.println("Class Annotations: " + response.getTotalClassAnnotations());
        System.out.println("Method Annotations: " + response.getTotalMethodAnnotations());
        System.out.println("Field Annotations: " + response.getTotalFieldAnnotations() + "\n");
        System.out.println("Message: " + response.getMessage());
    }

    public static void exportReport(String baseFilePath, AnnotationScanResponse response) {
        File outputFile = new File(baseFilePath);
        File parentFile = outputFile.getParentFile();
        //makes sure that new directory with jar file is formed only if it doesn't exist
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }

        File outDir = new File("result-scan-report");
        if (!outDir.exists()) outDir.mkdirs();

        ExportUtils.exportToJson(baseFilePath + ".json", response.getResults());
        ExportUtils.exportToCsv(baseFilePath + ".csv", response.getResults());
        ExportUtils.exportSummary(baseFilePath + "-summary.txt", response);
    }
}
