package com.zealthy.onboarding.entity;

public enum ComponentType {
    ABOUT_ME("About Me"),
    ADDRESS("Address"),
    BIRTHDATE("Birthdate");

    private final String displayName;

    ComponentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}