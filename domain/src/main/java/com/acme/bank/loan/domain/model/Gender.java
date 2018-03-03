package com.acme.bank.loan.domain.model;

@SuppressWarnings({"unused"})
public enum Gender {

    F("Female"),
    M("Male");

    private String name;

    Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
