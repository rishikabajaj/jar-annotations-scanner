package com.datastealth;

import com.datastealth.model.AnnotationScanResponseBuilder;
import com.datastealth.model.AnnotationScanResult;
import com.datastealth.model.AnnotationScanResponse;
import com.datastealth.reporter.AnnotationScanReporter;
import com.datastealth.scanner.JarAnnotationScanner;

import com.datastealth.service.AnnotationScanService;
import com.datastealth.service.impl.AnnotationScanServiceImpl;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AnnotationScannerTest {

    private static final String SAMPLE_JAR_PATH = "src/test/resources/sample.jar";
    private static final String REPORT_SAMPLE = "test-output/scan-report-sample";

    private File jsonFile;
    private File csvFile;
    private File summaryFile;

    private JarAnnotationScanner scanner;

    @BeforeAll
    void setupScanner() {
        scanner = new JarAnnotationScanner(new AnnotationScanServiceImpl());
    }

    @BeforeEach
    void setUp() {
        jsonFile = new File(REPORT_SAMPLE + ".json");
        csvFile = new File(REPORT_SAMPLE + ".csv");
        summaryFile = new File(REPORT_SAMPLE + "-summary.txt");

        File outputDir = new File("test-output");
        if (!outputDir.exists())
            outputDir.mkdirs();
    }

    @AfterEach
    void tearDown() {
        if (jsonFile.exists())
            jsonFile.delete();
        if (csvFile.exists())
            csvFile.delete();
        if (summaryFile.exists())
            summaryFile.delete();
    }

    @Test
    @DisplayName("1. Should throw error for invalid JAR path")
    void testInvalidJarPath() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            scanner.scanJar("non-existent.jar");
        });
        assertTrue(exception.getMessage().contains("JAR file does not exist"));
    }

    @Test
    @DisplayName("2. Should detect annotations from sample JAR")
    void testScanSampleJar() {
        AnnotationScanResponse response = scanner.scanJar(SAMPLE_JAR_PATH);
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertTrue(response.getTotalClasses() > 0);
    }

    @Test
    @DisplayName("3. Should export JSON, CSV, Summary files correctly")
    void testExportingResults() throws IOException {
        List<AnnotationScanResult> results = mockResults();
        AnnotationScanResponse response = AnnotationScanResponseBuilder.build(results);
        AnnotationScanReporter.exportReport(REPORT_SAMPLE, response);

        assertAll(
                () -> assertTrue(jsonFile.exists(), "JSON file should exist"),
                () -> assertTrue(csvFile.exists(), "CSV file should exist"),
                () -> assertTrue(summaryFile.exists(), "Summary file should exist"),
                () -> assertTrue(Files.readString(jsonFile.toPath()).contains("TestClass")),
                () -> assertTrue(Files.readString(csvFile.toPath()).contains("testMethod")),
                () -> assertTrue(Files.readString(summaryFile.toPath()).contains("Class Count")));
    }

    @Test
    @DisplayName("4. Should detect no annotations in empty class")
    void testEmptyAnnotations() {
        AnnotationScanResult result = new AnnotationScanResult("EmptyClass");
        assertFalse(result.hasAnnotations());
    }

    @Test
    @DisplayName("5. Should return message: No annotations found")
    void testNoAnnotationsMessage() {
        List<AnnotationScanResult> emptyResults = new ArrayList<>();
        AnnotationScanResponse response = AnnotationScanResponseBuilder.build(emptyResults);

        assertEquals("0 annotations found in the shared jar file", response.getMessage());
        assertEquals(0, response.getTotalClassAnnotations());
        assertEquals(0, response.getTotalMethodAnnotations());
        assertEquals(0, response.getTotalFieldAnnotations());
    }

    private List<AnnotationScanResult> mockResults() {
        AnnotationScanResult result = new AnnotationScanResult("TestClass");
        result.addClassAnnotation("@Deprecated");
        result.addMethodAnnotations("testMethod", List.of("@Override"));
        result.addFieldAnnotations("testField", List.of("@Inject"));

        List<AnnotationScanResult> results = new ArrayList<>();
        results.add(result);
        return results;
    }
}
