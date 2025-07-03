package com.datastealth;

import com.datastealth.model.AnnotationScanResponse;
import com.datastealth.reporter.AnnotationScanReporter;
import com.datastealth.scanner.JarAnnotationScanner;

import java.io.File;

public class ScanDriver {
    public static void main(String[] args) {
        // validates that one argument (containing path of jar file) is entered.
        if (args.length != 1) {
            System.out.println("Missing or Invalid Argument || \n Please run: java -jar annotation-inspector.jar <path-to-jar>");
            return;
        }

        String jarPath = args[0];

        File jarFile = new File(jarPath);
        if (!jarFile.exists()) {
            System.err.println("Provided JAR file does not exist: " + jarPath);
            return;
        }

        try {
            //scans jar file and returns response
            AnnotationScanResponse response = JarAnnotationScanner.scanJar(jarPath);
            String jarName = jarFile.getName().replace(".jar", "");
            String baseOutputPath = "result-scan-report" + File.separator + jarName + File.separator + jarName;

            AnnotationScanReporter.printReport(response);
            AnnotationScanReporter.exportReport(baseOutputPath, response);

            System.out.println("\nReports generated in: " + baseOutputPath + " (.json, .csv, .txt)");
        } catch (Exception e) {
            System.err.println("Failed to scan JAR: " + e.getMessage());
        }
    }
}
