package com.codingwithmitch.notes.models;

public class Student {

    private Long id;
    private boolean phone;
    private String university;
    private Long personId;

    public Student(Long id, boolean phone, String university, Long personId) {
        this.id = id;
        this.phone = phone;
        this.university = university;
        this.personId = personId;
    }

    public Long getId() {
        return id;
    }

    public String getUniversity() {
        return university;
    }

    public Long getPersonId() {
        return personId;
    }
}
