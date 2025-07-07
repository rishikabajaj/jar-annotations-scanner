# Changelog

## [1.0.1] - Unreleased - 2025-07-06

### Added
- Added service class to segregate business logic.
- Added interface to access the service to maintain abstraction.

### Changed
- Refactored AnnotationScanResponse logic into builder pattern to make it SRP compliant, follow clean code and testable.
- Switched from static method to instance-based JarAnnotationScanner.
- Used SLF4J for proper logging.
- Converted Enumeration (traditionally used method) in JarAnnotationScanner to List<> for better usability.

---

## [1.0.0] - 2025-07-03

### Added
- Base implementation to scan JAR files and detect annotations.
- Added model to mode the response and results of different classes.
- Added reporter to report the scan on console and export reports.
- Added scanner to scan classes, fields and methods.
- Added util to add utility files.
- Output File format: JSON, CSV, TXT (for summary).