package com.codingwithmitch.notes.models;

import java.time.LocalDateTime;

public class Person

{
    private Long id;
    private String name;
    private String surname;
    private String passport;
    private String phone;
    private Long externalId;
    private String created;
    private String updated;

    public Person(String name, String surname, String passport, String phone) {
        this.name = name;
        this.surname = surname;
        this.passport = passport;
        this.phone = phone;
    }

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

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }
}
