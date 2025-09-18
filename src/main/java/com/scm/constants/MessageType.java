package com.scm.constants;

/**
 * Enum representing different message types.
 * Each type has a human-readable display value and optionally a color code.
 */
public enum MessageType {

    BLUE("Blue Message", "text-blue-800 border-blue-300 bg-blue-50"),
    GREEN("Green Message", "text-green-800 border-green-300 bg-green-50"),
    RED("Red Message", "text-red-800 border-red-300 bg-red-50"),
    YELLOW("Yellow Message", "text-yellow-800 border-yellow-300 bg-yellow-50"),
    SUCCESS("Success", "text-green-800 border-green-300 bg-green-50"),
    WARNING("Warning", "text-yellow-800 border-yellow-300 bg-yellow-50"),
    FAILED("Failed", "text-red-800 border-red-300 bg-red-50"),
    ERROR("Error", "text-red-800 border-red-300 bg-red-50"),
    REGISTRATION_SUCCESSFULL("Registration Successful...", "text-green-800 border-green-300 bg-green-50");

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

    /** Override toString() to return display value */
    @Override
    public String toString() {
        return displayValue;
    }
}
