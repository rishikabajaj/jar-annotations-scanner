package com.datastealth.model;

import java.util.List;

public class AnnotationScanResponse {
    private final List<AnnotationScanResult> results;
    private final int totalClasses;
    private final int totalClassAnnotations;
    private final int totalMethodAnnotations;
    private final int totalFieldAnnotations;
    private final String message;

    public AnnotationScanResponse(List<AnnotationScanResult> results) {
        this.results = results;
        this.totalClasses = results.size();
        this.totalClassAnnotations = results.stream()
                .mapToInt(r -> r.getClassAnnotations().size())
                .sum();
        this.totalMethodAnnotations = results.stream()
                .mapToInt(r -> r.getMethodAnnotations().values().stream().mapToInt(List::size).sum())
                .sum();
        this.totalFieldAnnotations = results.stream()
                .mapToInt(r -> r.getFieldAnnotations().values().stream().mapToInt(List::size).sum())
                .sum();

        int totalAnnotations = totalClassAnnotations + totalMethodAnnotations + totalFieldAnnotations;

        if (totalAnnotations == 0) {
            this.message = "0 annotations found in the shared jar file";
        } else {
            this.message = totalAnnotations + " annotations found in the shared jar file";
        }
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
