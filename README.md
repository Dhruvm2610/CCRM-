This document summarizes the **Campus Course & Records Manager (CCRM)** project, its technical details, and a guide on how to run it.

---

## 🏛️ Campus Course & Records Manager (CCRM) - Project Summary

The **CCRM** is a comprehensive **console-based Java SE application** designed for educational institutes. It efficiently manages academic operations like students, courses, enrollments, grades, and records, featuring robust file operations and data persistence.

### Key Functional Features

* **Student Management**: Create, update, and manage student profiles and enrollment status.
* **Course Management**: Handle course creation, updates, and instructor assignments.
* **Enrollment System**: Manage student course enrollments with business rule validation.
* **Grading & Transcripts**: Record grades, calculate **GPA**, and generate academic transcripts.
* **File Operations**: Import/export data via **CSV files** with backup and archival capabilities (using **NIO.2**).
* **Reporting**: Generate various academic reports using the modern **Java Stream API**.

---

## ⚙️ How to Run the Application

### Prerequisites

* **JDK Version**: **Java 11 or higher**.
* **IDE**: Eclipse IDE for Java Developers (recommended).
* **Operating System**: Windows 10/11 (installation guide provided).

### Execution Commands

| Action | Command | Notes |
| :--- | :--- | :--- |
| **Clone** Repository | `git clone [your-repository-url]` | Then `cd CCRM` |
| **Compile** Project | `javac -d bin src/edu/ccrm/**/*.java` | |
| **Run** Application | `java -cp bin edu.ccrm.cli.Main` | |
| **Run with Assertions** | `java -ea -cp bin edu.ccrm.cli.Main` | Recommended for testing/debugging. |

---

## 💻 Java Architecture Explained

| Component | Description | Composition |
| :--- | :--- | :--- |
| **JVM** (Java Virtual Machine) | Executes Java bytecode. Provides platform independence, automatic garbage collection, and security. | Runtime engine |
| **JRE** (Java Runtime Environment) | Contains everything to **run** Java applications. | **JVM** + Core Libraries + Supporting Files |
| **JDK** (Java Development Kit) | The complete **development** environment. | **JRE** + Development Tools (javac, jdb, javadoc, etc.) |

**Interaction Flow:**
`Source Code (.java)` → `[javac]` → `Bytecode (.class)` → `[JVM]` → `Native Machine Code` → `Execution`

---

## 📜 Technical Implementation Mapping

The project demonstrates comprehensive use of **Java SE** features and **OOP principles**:

### Object-Oriented Programming (OOP)

* **Encapsulation**: Private fields with getters/setters in `Student`, `Course` classes.
* **Inheritance**: `edu.ccrm.domain.Person` is extended by `Student` and `Instructor`.
* **Abstraction**: `edu.ccrm.domain.Person` is an **Abstract class**.
* **Polymorphism**: Used in `edu.ccrm.service.*` interfaces.

### Advanced Java Features

* **Interfaces**: `edu.ccrm.service.Persistable` (Generic with default methods).
* **Enums**: Used for `edu.ccrm.domain.Grade` and `Semester` (with constructors/fields).
* **Exception Handling**: Custom exceptions like `DuplicateEnrollmentException`.
* **Collections Framework**: **HashMap** for storage, **List** for collections.
* **Generics**: Used in the `Persistable<T>` interface.
* **Lambda Expressions**: Used for Student sorting and course filtering predicates.
* **Stream API**: Used in `TranscriptService.generateReports()` for GPA, filtering, and aggregation.
* **NIO.2**: Used in `ImportExportService` for efficient CSV file operations.
* **Design Patterns**: **Singleton** (`AppConfig`) and **Builder** (`Course.Builder`).
* **Assertions**: Used throughout service classes for invariant checking (enabled with `-ea`).

---

## 📦 Package Structure and Architecture

The code is modular with a clean separation of concerns:

* `cli/`: Command Line Interface, main entry point.
* `config/`: Application configuration (`AppConfig` Singleton).
* `domain/`: Core Domain Models (e.g., `Student`, `Course`, `Person` Abstract class).
* `io/`: Input/Output Operations (`ImportExportService`).
* `service/`: Business Logic Layer (e.g., `StudentService`, `EnrollmentService`).
* `util/`: Utility Classes (e.g., `BackupUtility`, `RecursiveUtils`).

---

## ⏳ Evolution of Java - Key Milestones

* **1991**: Java project started (originally "Oak").
* **1995**: **Java 1.0** released ("Write Once, Run Anywhere").
* **1998**: **Java 2 (J2SE 1.2)**: Collections framework, Swing.
* **2004**: **Java 5 (J2SE 1.5)**: Generics, Annotations, Enums.
* **2014**: **Java 8**: **Lambda expressions**, **Stream API**.
* **2017**: **Java 9**: **Module system**, JShell.
* **2018**: **Java 10/11 (LTS)**: `var` keyword, HTTP client.
* **2021**: **Java 17 (LTS)**: Sealed classes, pattern matching.
* **2022-2024**: **Java 18-21**: Virtual threads, pattern matching enhancements.

---

## 📚 Java Editions Comparison

| Edition | Target Platform | Application Types | Core APIs | Status |
| :--- | :--- | :--- | :--- | :--- |
| **Java ME** (Micro) | Mobile devices, embedded systems | IoT devices, Mobile apps | Limited subset | Legacy (replaced by Android) |
| **Java SE** (Standard) | Desktop applications, servers | Standalone applications | Complete Java API set | **Active development** |
| **Java EE** (Enterprise) | Enterprise web applications | Web services, enterprise apps | Java SE + enterprise APIs (Servlets, JPA) | Transferred to Eclipse Foundation |

---

## 🛠️ Installation & IDE Setup

### JDK Installation on Windows

1.  **Download**: Get the **Windows x64 Installer** for Java 11+ from Oracle or OpenJDK.
2.  **Install**: Run the installer and accept default paths.
3.  **Environment Variables**:
    * Set **JAVA\_HOME** to the installation path (e.g., `C:\Program Files\Java\jdk-[version]`).
    * Add `%JAVA_HOME%\bin` to the **PATH** variable.
4.  **Verify**: Run `java -version` and `javac -version` in Command Prompt.

### Eclipse IDE Setup

1.  **Download/Launch**: Get **Eclipse IDE for Java Developers** and select a workspace.
2.  **New Project**: **File** → **New** → **Java Project**.
3.  **Run Config**: Use **VM arguments** `-ea` in **Run Configurations** → **Arguments** tab to enable assertions.

---

## 📂 Sample Data and Usage

* **Sample Data Location**: `test-data/students.csv` and `test-data/courses.csv`.
* **Importing**:
    1.  Start the application.
    2.  Select option **5. Import/Export Data** from the main menu.
    3.  Choose to import and use the default path (`y`) or provide a full path (`n`).

---

## ℹ️ Project Details and Acknowledgments

* **Author**: Dhruv Maheshwari
* **Reg. No.**: 24BCE10610
* **Course**: Programming in Java
* **Institution**: Vellore Institute of Technology, Bhopal
* **Date**: 24-09-2025
* **Version**: 1.0

This project serves as a demonstration of comprehensive Java SE features and software engineering best practices.
