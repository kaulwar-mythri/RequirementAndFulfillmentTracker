package org.accolite.RequirementAndFulfillmentTracker.entity;

public enum Experience {
    JUNIOR("Junior"),
    MID("Mid"),
    SENIOR("Senior"),
    MID_SENIOR("Mid Senior"),
    ADVANCE("Advance"),
    EXPERT("Expert"),
    OTHER("Other");

    private final String displayName;

    Experience(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
