package com.datastealth.model;

import java.util.*;

public class AnnotationScanResult {
    private final String className;
    private final List<String> classAnnotations = new ArrayList<>();
    private final Map<String, List<String>> methodAnnotations = new HashMap<>();
    private final Map<String, List<String>> fieldAnnotations = new HashMap<>();

    public AnnotationScanResult(String className) {
        this.className = className;
    }

    public String getClassName() { return className; }

    public List<String> getClassAnnotations() { return classAnnotations; }

    public Map<String, List<String>> getMethodAnnotations() { return methodAnnotations; }

    public Map<String, List<String>> getFieldAnnotations() { return fieldAnnotations; }

    public void addClassAnnotation(String annotation) {
        classAnnotations.add(annotation);
    }

    public void addMethodAnnotations(String methodName, List<String> annotations) {
        methodAnnotations.put(methodName, annotations);
    }

    public void addFieldAnnotations(String fieldName, List<String> annotations) {
        fieldAnnotations.put(fieldName, annotations);
    }

    public boolean hasAnnotations() {
        return !(classAnnotations.isEmpty() && methodAnnotations.isEmpty() && fieldAnnotations.isEmpty());
    }
}
