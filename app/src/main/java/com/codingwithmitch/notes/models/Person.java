package com.codingwithmitch.notes.models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Person implements Serializable

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

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
