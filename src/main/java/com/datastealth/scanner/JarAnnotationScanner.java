package com.datastealth.scanner;

import com.datastealth.model.AnnotationScanResponseBuilder;
import com.datastealth.model.AnnotationScanResult;
import com.datastealth.model.AnnotationScanResponse;

import java.io.File;
import java.io.IOException;

import com.datastealth.service.AnnotationScanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

// scans the jar files for class level, method level and field level annotations
public class JarAnnotationScanner {
    private static final Logger logger = LoggerFactory.getLogger(JarAnnotationScanner.class);
    private final AnnotationScanService scanService;

    public JarAnnotationScanner(AnnotationScanService scanService) {
        this.scanService = scanService;
    }

    public AnnotationScanResponse scanJar(String jarPath) {

        File jarFile = new File(jarPath);
        if (!jarFile.exists()) {
            throw new IllegalArgumentException("JAR file does not exist: " + jarPath);
        }

        List<AnnotationScanResult> results = new ArrayList<>();
        
        //used try-with-resources to make sure that file is closed after use
        try (
                URLClassLoader loader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()});
                JarFile jar = new JarFile(jarFile)
        ) {
            List<JarEntry> classEntries = Collections.list(jar.entries()).stream()
                    .filter(e -> e.getName().endsWith(".class"))
                    .toList();

            logger.info("Starting annotation scan on {} classes", classEntries.size());

            for (JarEntry entry : classEntries) {
                String className = entry.getName().replace("/", ".").replace(".class", "");
                try {
                    Class<?> loadedClass = loader.loadClass(className);
                    AnnotationScanResult result = scanService.scanClass(loadedClass);

                    if (result.hasAnnotations()) {
                        results.add(result);
                    }
                } catch (Throwable e) {
                    logger.warn("Skipping class: {} due to error: {}", className, e.getMessage());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to scan JAR: " + e.getMessage());
        }

        return AnnotationScanResponseBuilder.build(results);
    }
}
