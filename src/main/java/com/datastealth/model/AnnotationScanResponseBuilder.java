package com.datastealth.model;


import java.util.List;

public class AnnotationScanResponseBuilder {
    public static AnnotationScanResponse build(List<AnnotationScanResult> results) {
        int totalClasses = results.size();

        int totalClassAnnotations = results.stream()
                .mapToInt(r -> r.getClassAnnotations().size())
                .sum();

        int totalMethodAnnotations = results.stream()
                .mapToInt(r -> r.getMethodAnnotations().values().stream().mapToInt(List::size).sum())
                .sum();

        int totalFieldAnnotations = results.stream()
                .mapToInt(r -> r.getFieldAnnotations().values().stream().mapToInt(List::size).sum())
                .sum();

        int totalAnnotations = totalClassAnnotations + totalMethodAnnotations + totalFieldAnnotations;

        String message = totalAnnotations == 0
                ? "0 annotations found in the shared jar file"
                : totalAnnotations + " annotations found in the shared jar file";

        return new AnnotationScanResponse(
                results,
                totalClasses,
                totalClassAnnotations,
                totalMethodAnnotations,
                totalFieldAnnotations,
                message
        );
    }
}
