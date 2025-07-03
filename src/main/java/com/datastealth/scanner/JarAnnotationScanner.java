package com.datastealth.scanner;

import com.datastealth.model.AnnotationScanResult;
import com.datastealth.model.AnnotationScanResponse;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarAnnotationScanner {
    public static AnnotationScanResponse scanJar(String jarPath) {
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
            Enumeration<JarEntry> entries = jar.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.getName().endsWith(".class")) continue;

                String className = entry.getName().replace("/", ".").replace(".class", "");
                try {
                    Class<?> loadedClass = loader.loadClass(className);
                    AnnotationScanResult result = new AnnotationScanResult(className);

                    for (Annotation annotation : loadedClass.getAnnotations()) {
                        result.addClassAnnotation(annotation.annotationType().getName());
                    }

                    for (Method method : loadedClass.getDeclaredMethods()) {
                        List<String> methodAnnotations = new ArrayList<>();
                        for (Annotation annotation : method.getAnnotations()) {
                            methodAnnotations.add(annotation.annotationType().getName());
                        }
                        if (!methodAnnotations.isEmpty()) {
                            result.addMethodAnnotations(method.getName(), methodAnnotations);
                        }
                    }

                    for (Field field : loadedClass.getDeclaredFields()) {
                        List<String> fieldAnnos = new ArrayList<>();
                        for (Annotation annotation : field.getAnnotations()) {
                            fieldAnnos.add(annotation.annotationType().getName());
                        }
                        if (!fieldAnnos.isEmpty()) {
                            result.addFieldAnnotations(field.getName(), fieldAnnos);
                        }
                    }

                    if (result.hasAnnotations()) {
                        results.add(result);
                    }
                } catch (Throwable e) {
                    System.err.println("Skipping class: " + className + " due to error: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to scan JAR: " + e.getMessage());
        }

        return new AnnotationScanResponse(results);
    }
}
