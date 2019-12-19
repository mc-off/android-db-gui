package com.codingwithmitch.notes.models;

import java.time.LocalDateTime;

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

    public boolean isPhone() {
        return phone;
    }

    public String getUniversity() {
        return university;
    }

    public Long getPersonId() {
        return personId;
    }
}
