package com.scm.constants;

/**
 * Enum representing different message types.
 * Each type has a human-readable display value and optionally a color code.
 */
public enum MessageType {

    BLUE("Blue Message", "#0000FF"),
    GREEN("Green Message", "#00FF00"),
    RED("Red Message", "#FF0000"),
    YELLOW("Yellow Message", "#FFFF00"),
    SUCCESS("Success", "#28a745"),
    WARNING("Warning", "#ffc107"),
    FAILED("Failed", "#dc3545"),
    ERROR("Error", "#FF0000");

    /** Human-readable display string */
    private final String displayValue;

    /** Optional color code in HEX */
    private final String colorCode;

    /**
     * Constructor for the enum.
     *
     * @param displayValue Human-readable name
     * @param colorCode    HEX color code
     */
    MessageType(String displayValue, String colorCode) {
        this.displayValue = displayValue;
        this.colorCode = colorCode;
    }

    /** Returns the display string */
    public String getDisplayValue() {
        return displayValue;
    }

    /** Returns the color code */
    public String getColorCode() {
        return colorCode;
    }

    /** Returns the vlaue of ENUM */
    public String valueOf() {
        return this.name();
    }

    /** Override toString() to return display value */
    @Override
    public String toString() {
        return displayValue;
    }
}
