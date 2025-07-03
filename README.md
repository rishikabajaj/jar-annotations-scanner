# JAR Annotation Scanner

## Overview

JAR Annotation Scanner is a Java tool that scans any `.jar` file and detects usage of Java annotations.

---

## Features

* Scans `.jar` files for runtime annotations.
* Supports detection at all three levels:
    * Class-level annotations
    * Method-level annotations
    * Field-level annotations
* Outputs reports in CSV and JSON File formats and are available under **./result-scan-report/<jar-name>**:
    * `scan-report-<jarname>.json`
    * `scan-report-<jarname>.csv`
    * `scan-report-<jarname>-summary.txt`

---
## Flowchart

```text
     [ Begin  - Jar File is shared]
                 ↓
  Accept JAR file path in the form of arg 
                 ↓
        JAR file path is validated
                 ↓
    .class entries are loaded from JAR
                 ↓
   Each class is loaded through reflection
                 ↓
Class, Method and Field Annotations are extracted
                 ↓
     Summary is displayed on output
                 ↓
JSON and CSV File is exported. Summary of the annotations is also exported
                 ↓
 [ Done - Summary is displayed on console]
```

---

## Project Structure

```
annotation-scanner/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/datastealth/
│   │           ├── ScanDriver.java
│   │           ├── scanner/
│   │           │   └── JarAnnotationScanner.java
│   │           ├── model/
│   │           │   ├── AnnotationScanResult.java
│   │           │   └── AnnotationScanResponse.java
│   │           ├── reporter/
│   │           │   ├── AnnotationScanReporter.java
│   │           └── util/
│   │               └── ExportUtil.java
│
│   └── test/
│       └── java/
│           └── com/datastealth/
│               └── AnnotationScannerTest.java
│
│       └── resources/
│           └── sample.jar
```

---
## How to Build

To generate a JAR file with dependencies at target:
```bash
mvn clean package
```

The above command will generate

```pgsql
target/jar-annotation-scanner-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## How to Run

```bash 
java -jar target/jar-annotation-scanner-1.0-SNAPSHOT-jar-with-dependencies.jar <path-to-your-jar>
```

Sample Run Scenarios for this Tool:
```bash
java -jar target/annotation-scanner-1.0-SNAPSHOT-jar-with-dependencies.jar ./test-jar-files/sample/target/sample-lib-1.0-SNAPSHOT.jar
```
```bash
java -jar target/annotation-scanner-1.0-SNAPSHOT-jar-with-dependencies.jar ./test-jar-files/sample-empty/target/sample-empty-1.0-SNAPSHOT.jar
```
```bash
java -jar target/annotation-scanner-1.0-SNAPSHOT-jar-with-dependencies.jar ./test-jar-files/sample-annotations/target/sample-annotations-1.0-SNAPSHOT.jar
```
```bash
java -jar target/annotation-scanner-1.0-SNAPSHOT-jar-with-dependencies.jar ./test-jar-files/sample-nested/target/sample-nested-1.0-SNAPSHOT.jar
```

Output Reports:
* After running, we’ll get:

```pgsql
result-scan-report/
└──  scan-report-sample-lib-1.0-SNAPSHOT
      ├── scan-report-sample-lib-1.0-SNAPSHOT.json
      ├── scan-report-sample-lib-1.0-SNAPSHOT.csv
      └── scan-report-sample-lib-1.0-SNAPSHOT-summary.txt

```
Each file contains a detailed mapping of annotations found in the scanned JAR in both the formats.

---

## Error Handling

* If invalid JAR path is provided : Tool will print user-friendly error and exits.
* If a particular class is not getting loaded : skips the class and warning is logged.
* If a JAR is malformed : exception handled using `IOException`.
* If there is no annotation in the file : report is generated with the message.

---

## Edge Cases
* No jar file is provided : handled gracefully.
* Multiple jar files are provided : can be handled and steps are provided in the future scopes.
* Malformed jar file is provided :  handled gracefully.
* A jar file is provided with all the annotations, no annotations, nested/partial annotations.
* A jar file is provided with method only annotations, field only annotations.
* There are malformed annotations in the jar file : skipped gracefully and informed

---

## Sample Jar Files
* Sample jar files are provided in test-jar-files folder 
---

## Future Scope

* **Batch Scanning** multiple JARs in a folder.
* Handling **Versioning** of the exports and retaining the older versions as well
* Exporting report to frontend for better viewing.

---

## Handling Large Number of Annotations

* For extreme volumes:
    * Use streaming or paginated output
    * Buffered I/O writing
    * Use threads to parallelize scanning of classes

---

## Test Cases Covered

* Test with invalid JAR path (throws exception)
* Detect annotations from sample JAR
* Export to JSON, CSV, Summary files correctly
* Test empty class (no annotation)
* Test return message in case No annotations found
* Test malformed annotation file - testing empty files
---
