package com.datastealth.service.impl;

import com.datastealth.model.AnnotationScanResult;
import com.datastealth.service.AnnotationScanService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationScanServiceImpl implements AnnotationScanService {
    @Override
    public AnnotationScanResult scanClass(Class<?> loadedclass) {
        AnnotationScanResult result = new AnnotationScanResult(loadedclass.getName());

        // Class-level annotations
        for (Annotation annotation : loadedclass.getAnnotations()) {
            result.addClassAnnotation(annotation.annotationType().getName());
        }

        // Method-level annotations
        for (Method method : loadedclass.getDeclaredMethods()) {
            List<String> methodAnnotations = new ArrayList<>();
            for (Annotation annotation : method.getAnnotations()) {
                methodAnnotations.add(annotation.annotationType().getName());
            }
            if (!methodAnnotations.isEmpty()) {
                result.addMethodAnnotations(method.getName(), methodAnnotations);
            }
        }

        // Field-level annotations
        for (Field field : loadedclass.getDeclaredFields()) {
            List<String> fieldAnnotations = new ArrayList<>();
            for (Annotation annotation : field.getAnnotations()) {
                fieldAnnotations.add(annotation.annotationType().getName());
            }
            if (!fieldAnnotations.isEmpty()) {
                result.addFieldAnnotations(field.getName(), fieldAnnotations);
            }
        }

        return result;
    }
}
