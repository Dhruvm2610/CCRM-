package edu.ccrm.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a student in the Campus Course & Records Manager system.
 * This class extends Person and includes student-specific details like registration number,
 * enrolled courses, and their academic transcript.
 */
public class Student extends Person {
    private String registrationNumber;
    private List<String> enrolledCourses; // Keeping track of course codes the student is enrolled in
    
    // This map stores the student's academic record, mapping course codes to their transcript entries.
    private Map<String, TranscriptEntry> transcript = new HashMap<>();
 
   
    public Student(String personId, String registrationNumber, String fullName, String email) {
        super(personId, fullName, email);
        this.registrationNumber = registrationNumber;
        this.enrolledCourses = new ArrayList<>();
    }

    public String getRegistrationNumber() { return registrationNumber; }
    public List<String> getEnrolledCourses() { return enrolledCourses; }

    public Map<String, TranscriptEntry> getTranscript() { return transcript; }

    /**
     * Adds a new entry to the student's academic transcript.
     * @param courseCode The code of the course.
     * @param marks The marks obtained in the course.
     * @param grade The grade received in the course.
     */
    public void addTranscriptEntry(String courseCode, int marks, Grade grade) {
        transcript.put(courseCode, new TranscriptEntry(courseCode, marks, grade));
    }

    /**
     * Enrolls the student in a specified course.
     * We only add the course code if they aren't already enrolled, to avoid duplicates.
     * @param courseCode The code of the course to enroll in.
     */
    public void enroll(String courseCode) {
        if (!enrolledCourses.contains(courseCode))
            enrolledCourses.add(courseCode);
    }

    /**
     * Unenrolls the student from a specified course.
     * @param courseCode The code of the course to unenroll from.
     */
    public void unenroll(String courseCode) {
        enrolledCourses.remove(courseCode);
    }

    /**
     * Provides a detailed profile string for the student, including personal and academic info.
     * @return A formatted string with the student's profile.
     */
    @Override
    public String getProfile() {
        return "Student [" + super.toString() + ", RegistrationNumber: " + registrationNumber + ", Enrolled Courses: " + enrolledCourses + "]";
    }
}