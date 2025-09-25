package edu.ccrm.domain;

/**
 * Represents a course offered in the Campus Course & Records Manager system.
 * It holds details like the course code, title, credits, and assigned instructor.
 */
public class Course {
    private String courseCode;
    private String title;
    private int credits;
    private String instructorId; // This refers to the Person ID of the instructor
    private Semester semester;
    private String department;

    public Course(String courseCode, String title, int credits, String instructorId, Semester semester, String department) {
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
        this.instructorId = instructorId;
        this.semester = semester;
        this.department = department;
    }


    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructorId() { return instructorId; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }

    public void setTitle(String title) { this.title = title; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }
    public void setSemester(Semester semester) { this.semester = semester; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return String.format("Course [%s: %s, Credits: %d, Instructor: %s, Semester: %s, Dept: %s]",
            courseCode, title, credits, instructorId, semester, department);
    }
}
