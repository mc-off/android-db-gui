package com.codingwithmitch.notes.models;

import java.time.LocalDateTime;

public class Student {

    private Long id;
    private String name;
    private String surname;
    private String passport;
    private String phone;
    private Long externalId;
    private LocalDateTime created;
    private LocalDateTime updated;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassport() {
        return passport;
    }

    public String getPhone() {
        return phone;
    }

    public Long getExternalId() {
        return externalId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }
}
