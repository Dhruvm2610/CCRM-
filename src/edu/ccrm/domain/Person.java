package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * This is a base class for any person in our system, like a student or an instructor.
 * It holds the common properties that all people share.
 */
public abstract class Person {
    protected String id;
    protected String name;
    protected String email;
    protected boolean active;
    protected LocalDate creationDate;

    public Person(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.creationDate = LocalDate.now();
        this.active = true;
    }

    public abstract String getProfile();

    // Standard getters and setters below
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean isActive() { return active; }
    public LocalDate getCreationDate() { return creationDate; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setActive(boolean active) { this.active = active; }

    // I'm overriding toString to give a nice summary of the person.
    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Email: %s, Active: %b, Since: %s",
            id, name, email, active, creationDate);
    }
}