package org.accolite.RequirementAndFulfillmentTracker.entity;

<<<<<<< HEAD

/*
*   Includes  each experience level with a range of experience years
* */
public enum Experience {
    JUNIOR("Junior", 0, 3),
    INTERMEDIATE("Intermediate", 3, 5),
    SENIOR("Senior", 5, 8),
    LEAD("Lead", 8, 11),
    EXPERT("Expert", 11, Integer.MAX_VALUE);

    private final String label;
    private final int minExperienceYears;
    private final int maxExperienceYears;

    Experience(String label, int minExperienceYears, int maxExperienceYears) {
        this.label = label;
        this.minExperienceYears = minExperienceYears;
        this.maxExperienceYears = maxExperienceYears;
    }

    public String getLabel() {
        return label;
    }


    public int getMinExperienceYears() {
        return minExperienceYears;
    }

    public int getMaxExperienceYears() {
        return maxExperienceYears;
=======
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
>>>>>>> 3d1190ba5eb171819a97d546125ea8c32921b9d2
    }
}
