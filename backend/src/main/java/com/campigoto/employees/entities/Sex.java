package com.campigoto.employees.entities;

public enum Sex {

    M("Male"), F("Female");

    private final String description;

    Sex(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
