package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;
import edu.ccrm.domain.TranscriptEntry;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.GradingService;
import edu.ccrm.service.StudentService;
import edu.ccrm.util.BackupUtility;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CLIApplication {

    // Services for handling business logic
    private final StudentService studentManager = new StudentService();
    private final CourseService courseManager = new CourseService();
    private final EnrollmentService enrollmentManager = new EnrollmentService();
    private final GradingService gradingManager = new GradingService();
    private final ImportExportService dataTransferService = new ImportExportService();

    // I'm using a single scanner for all user input.
    private final Scanner inputScanner = new Scanner(System.in);

    // Access to application-wide configuration settings.
    private final AppConfig applicationConfiguration = AppConfig.getInstance();

    /**
     * This is the main entry point of the application.
     * It runs a loop to keep the application alive until the user decides to exit.
     */
    public void run() {
        boolean isApplicationRunning = true;
        while (isApplicationRunning) {
            displayMainMenu();
            int menuSelection = getUserChoice();
            isApplicationRunning = handleMenuChoice(menuSelection);
        }
        System.out.println("Exiting application. Goodbye!");
        inputScanner.close(); // Don't forget to close the scanner when we're done!
    }

    private void displayMainMenu() {
        System.out.println("\n==== Campus Course & Records Manager ====");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollments");
        System.out.println("4. Manage Grades");
        System.out.println("5. Import/Export Data");
        System.out.println("6. Backup Data");
        System.out.println("0. Exit");
        System.out.print("Please select an option: ");
    }

    private int getUserChoice() {
        try {
            int userChoice = inputScanner.nextInt();
            // I need to consume the rest of the line here, otherwise the next nextLine() call will not work correctly.
            inputScanner.nextLine(); 
            return userChoice;
        } catch (InputMismatchException e) {
            System.out.println("That's not a valid number. Please try again with a numeric option.");
            inputScanner.nextLine(); // Clear the invalid input from the scanner.
            return -1; // Return an invalid choice to re-display the menu.
        }
    }

    private boolean handleMenuChoice(int choice) {
        switch (choice) {
            case 0:
                return false; // This will terminate the main loop
            case 1:
                studentManagementMenu();
                break;
            case 2:
                courseManagementMenu();
                break;
            case 3:
                enrollmentManagementMenu();
                break;
            case 4:
                gradingManagementMenu();
                break;
            case 5:
                dataImportExportMenu();
                break;
            case 6:
                backupDataMenu();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
        return true;
    }

    // --- Student Management ---
    private void studentManagementMenu() {
        System.out.println("\n-- Student Management --");
        System.out.println("1. Add a new student");
        System.out.println("2. List all students");
        System.out.print("Select an option: ");
        int userChoice = getUserChoice();
        switch (userChoice) {
            case 1:
                addNewStudent();
                break;
            case 2:
                listAllStudents();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void addNewStudent() {
        System.out.print("Enter Student ID: ");
        String studentId = inputScanner.nextLine();
        System.out.print("Enter Registration Number: ");
        String registrationNumber = inputScanner.nextLine();
        System.out.print("Enter Full Name: ");
        String fullName = inputScanner.nextLine();
        System.out.print("Enter Email: ");
        String email = inputScanner.nextLine();

        Student student = new Student(studentId, registrationNumber, fullName, email);
        studentManager.addStudent(student);
        System.out.println("Student added successfully!");
    }

    private void listAllStudents() {
        System.out.println("\n-- All Students --");
        studentManager.listStudents().forEach(student -> System.out.println(student.getProfile()));
    }

    // --- Course Management ---
    private void courseManagementMenu() {
        System.out.println("\n-- Course Management --");
        System.out.println("1. Add a new course");
        System.out.println("2. List all courses");
        System.out.print("Select an option: ");
        int userChoice = getUserChoice();
        switch (userChoice) {
            case 1:
                addNewCourse();
                break;
            case 2:
                listAllCourses();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void addNewCourse() {
        System.out.print("Enter Course Code: ");
        String courseCode = inputScanner.nextLine();
        System.out.print("Enter Course Title: ");
        String title = inputScanner.nextLine();
        System.out.print("Enter Credits (as an integer): ");
        int credits = inputScanner.nextInt();
        inputScanner.nextLine(); // Clearing the buffer
        System.out.print("Enter Instructor ID (optional): ");
        String instructorId = inputScanner.nextLine();
        System.out.print("Enter Semester (e.g., SPRING, SUMMER, FALL): ");
        String semesterString = inputScanner.nextLine().toUpperCase();
        Semester semester = Semester.valueOf(semesterString);
        System.out.print("Enter Department: ");
        String department = inputScanner.nextLine();

        Course course = new Course(courseCode, title, credits, instructorId, semester, department);
        courseManager.addCourse(course);
        System.out.println("Course added successfully!");
    }

    private void listAllCourses() {
        System.out.println("\n-- All Courses --");
        courseManager.listCourses().forEach(System.out::println);
    }

    // --- Enrollment Management ---
    private void enrollmentManagementMenu() {
        System.out.println("\n-- Enrollment Management --");
        System.out.println("1. Enroll a student in a course");
        System.out.println("2. View a student's enrollments");
        System.out.print("Select an option: ");
        int userChoice = getUserChoice();
        switch (userChoice) {
            case 1:
                enrollStudentInCourse();
                break;
            case 2:
                viewStudentEnrollments();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void enrollStudentInCourse() {
        System.out.print("Enter Student Registration Number: ");
        String registrationNumber = inputScanner.nextLine();
        System.out.print("Enter Course Code: ");
        String courseCode = inputScanner.nextLine();

        Student student = studentManager.getStudent(registrationNumber);
        Course course = courseManager.listCourses().stream()
                .filter(c -> c.getCourseCode().equals(courseCode))
                .findFirst().orElse(null);

        if (student == null) {
            System.out.println("Error: Student not found with Registration Number: " + registrationNumber);
        } else if (course == null) {
            System.out.println("Error: Course not found with Code: " + courseCode);
        } else {
            boolean enrolled = enrollmentManager.enrollStudentInCourse(student, course);
            if (enrolled) {
                System.out.println("Enrollment successful!");
            } else {
                System.out.println("Enrollment failed. Please check enrollment rules.");
            }
        }
    }

    private void viewStudentEnrollments() {
        System.out.print("Enter Student Registration Number: ");
        String registrationNumber = inputScanner.nextLine();

        Student student = studentManager.getStudent(registrationNumber);
        if (student == null) {
            System.out.println("Error: Student not found.");
        } else {
            System.out.println(student.getProfile());
        }
    }

    // --- Grading Management ---
    private void gradingManagementMenu() {
        System.out.println("\n-- Grading Management --");
        System.out.println("1. Assign a grade to a student");
        System.out.println("2. View a student's transcript");
        System.out.print("Select an option: ");
        int userChoice = getUserChoice();
        switch (userChoice) {
            case 1:
                assignGradeToStudent();
                break;
            case 2:
                viewStudentTranscript();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void assignGradeToStudent() {
        System.out.print("Enter Student Registration Number: ");
        String registrationNumber = inputScanner.nextLine();
        System.out.print("Enter Course Code: ");
        String courseCode = inputScanner.nextLine();
        System.out.print("Enter Marks (0–100): ");
        int marks = inputScanner.nextInt();
        inputScanner.nextLine(); // Consume the rest of the line

        Student student = studentManager.getStudent(registrationNumber);
        if (student == null) {
            System.out.println("Error: Student not found with Registration Number: " + registrationNumber);
            return;
        }
        // I added this check to make sure the student is actually enrolled before assigning a grade.
        if (!student.getEnrolledCourses().contains(courseCode)) {
            System.out.println("Error: Student is not enrolled in this course.");
            return;
        }
        gradingManager.assignMarks(student, courseCode, marks);
        System.out.println("Marks and grade have been assigned successfully.");
    }

    private void viewStudentTranscript() {
        System.out.print("Enter Student Registration Number: ");
        String registrationNumber = inputScanner.nextLine();

        Student student = studentManager.getStudent(registrationNumber);
        if (student == null) {
            System.out.println("Error: Student not found.");
        } else {
            System.out.println("\n-- Transcript for " + student.getName() + " --");
            for (TranscriptEntry transcriptEntry : student.getTranscript().values()) {
                System.out.println(transcriptEntry);
            }
            double gradePointAverage = gradingManager.computeGPA(student);
            System.out.printf("Cumulative GPA: %.2f\n", gradePointAverage);
        }
    }

    // --- Data Import/Export ---
    private void dataImportExportMenu() {
        System.out.println("\n-- Data Import/Export --");
        System.out.println("1. Import students from CSV");
        System.out.println("2. Export students to CSV");
        System.out.println("3. Import courses from CSV");
        System.out.println("4. Export courses to CSV");
        System.out.print("Select an option: ");
        int userChoice = getUserChoice();
        switch (userChoice) {
            case 1:
                handleStudentImport();
                break;
            case 2:
                handleStudentExport();
                break;
            case 3:
                handleCourseImport();
                break;
            case 4:
                handleCourseExport();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void handleStudentImport() {
        String defaultFilePath = applicationConfiguration.getApplicationDataDirectory() + "/students.csv";
        String selectedFilePath = promptForFilePath("students.csv", defaultFilePath);
        if (selectedFilePath == null) return; // User cancelled or provided invalid input

        try {
            int importedFileCount = dataTransferService.importStudents(selectedFilePath, studentManager);
            System.out.println("Successfully imported " + importedFileCount + " students from " + selectedFilePath + ".");
        } catch (IOException e) {
            System.out.println("Error: Failed to import students. " + e.getMessage());
        }
    }

    private void handleStudentExport() {
        String defaultFilePath = applicationConfiguration.getApplicationDataDirectory() + "/students.csv";
        String selectedFilePath = promptForFilePath("students.csv", defaultFilePath);
        if (selectedFilePath == null) return; // User cancelled or provided invalid input

        try {
            dataTransferService.exportStudents(selectedFilePath, studentManager);
            System.out.println("Successfully exported students to " + selectedFilePath + ".");
        } catch (IOException e) {
            System.out.println("Error: Failed to export students. " + e.getMessage());
        }
    }

    private void handleCourseImport() {
        String defaultFilePath = applicationConfiguration.getApplicationDataDirectory() + "/courses.csv";
        String selectedFilePath = promptForFilePath("courses.csv", defaultFilePath);
        if (selectedFilePath == null) return; // User cancelled or provided invalid input

        try {
            int importedFileCount = dataTransferService.importCourses(selectedFilePath, courseManager);
            System.out.println("Successfully imported " + importedFileCount + " courses from " + selectedFilePath + ".");
        } catch (IOException e) {
            System.out.println("Error: Failed to import courses. " + e.getMessage());
        }
    }

    private void handleCourseExport() {
        String defaultFilePath = applicationConfiguration.getApplicationDataDirectory() + "/courses.csv";
        String selectedFilePath = promptForFilePath("courses.csv", defaultFilePath);
        if (selectedFilePath == null) return; // User cancelled or provided invalid input

        try {
            dataTransferService.exportCourses(selectedFilePath, courseManager);
            System.out.println("Successfully exported courses to " + selectedFilePath + ".");
        } catch (IOException e) {
            System.out.println("Error: Failed to export courses. " + e.getMessage());
        }
    }

    private String promptForFilePath(String defaultFileName, String defaultFullPath) {
        System.out.println("\nWould you like to use the default path for " + defaultFileName + "?");
        System.out.println("Default path: " + defaultFullPath);
        System.out.print("Enter 'y' for yes, or 'n' to provide a custom path: ");
        String userChoice = inputScanner.nextLine().trim().toLowerCase();

        if (userChoice.equals("y")) {
            return defaultFullPath;
        } else if (userChoice.equals("n")) {
            System.out.print("Please enter the full custom file path: ");
            return inputScanner.nextLine().trim();
        } else {
            System.out.println("Invalid choice. Using default path.");
            return defaultFullPath;
        }
    }

    // --- Backup ---
    private void backupDataMenu() {
        System.out.println("\n-- Data Backup --");
        String defaultSourceDirectory = applicationConfiguration.getApplicationDataDirectory();
        System.out.println("The default source directory for backup is: " + defaultSourceDirectory);
        System.out.print("Enter the destination directory for the backup (e.g., /home/user/backups): ");
        String backupDestinationPath = inputScanner.nextLine().trim();

        if (backupDestinationPath.isEmpty()) {
            System.out.println("Backup cancelled: Destination directory cannot be empty.");
            return;
        }

        try {
            Path backupSourcePath = Paths.get(defaultSourceDirectory);
            Path backupDestination = Paths.get(backupDestinationPath);

            // Just a little check to make sure the destination exists, or we can create it.
            if (!java.nio.file.Files.exists(backupDestination)) {
                java.nio.file.Files.createDirectories(backupDestination);
                System.out.println("Created backup destination directory: " + backupDestination);
            }

            BackupUtility.backupDirectory(backupSourcePath, backupDestination);
            System.out.println("Backup completed successfully from '" + backupSourcePath + "' to '" + backupDestination + "'!");
        } catch (Exception e) {
            System.out.println("Error: Backup failed. Something went wrong: " + e.getMessage());
            // For debugging, it's sometimes helpful to print the stack trace, but for a user, a simple message is better.
            // e.printStackTrace();
        }
    }
}
