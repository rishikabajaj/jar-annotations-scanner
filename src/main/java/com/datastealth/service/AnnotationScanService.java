package com.datastealth.service;

import com.datastealth.model.AnnotationScanResult;

public interface AnnotationScanService {
    AnnotationScanResult scanClass(Class<?> loadedClass);
}
