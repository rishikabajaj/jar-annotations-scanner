package com.datastealth.model;

import java.util.List;

//creates the aggregated response from scanning a JAR file for annotations showing total counts.
public class AnnotationScanResponse {
    private final List<AnnotationScanResult> results;
    private final int totalClasses;
    private final int totalClassAnnotations;
    private final int totalMethodAnnotations;
    private final int totalFieldAnnotations;
    private final String message;

    public AnnotationScanResponse(List<AnnotationScanResult> results,
                                  int totalClasses,
                                  int totalClassAnnotations,
                                  int totalMethodAnnotations,
                                  int totalFieldAnnotations,
                                  String message) {
        this.results = results;
        this.totalClasses = totalClasses;
        this.totalClassAnnotations = totalClassAnnotations;
        this.totalMethodAnnotations = totalMethodAnnotations;
        this.totalFieldAnnotations = totalFieldAnnotations;
        this.message = message;
    }

    public List<AnnotationScanResult> getResults() {
        return results;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public int getTotalClassAnnotations() {
        return totalClassAnnotations;
    }

    public int getTotalMethodAnnotations() {
        return totalMethodAnnotations;
    }

    public int getTotalFieldAnnotations() {
        return totalFieldAnnotations;
    }

    public boolean isEmpty() {
        return results.isEmpty();
    }

    public String getMessage() {
        return message;
    }

}
