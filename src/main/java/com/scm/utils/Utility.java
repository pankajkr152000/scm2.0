package com.scm.utils;

import org.springframework.util.StringUtils;

public class Utility {
    public static String firstNameFromString(String fullName) {

        String[] parts = fullName.trim().split("\\s+");
        if (parts[0].length() <= 1 && StringUtils.hasText(parts[1]) && !parts[1].isEmpty()) {
            if(parts[1].length() >= 1)
                return ((capitalizeFirstLetter(parts[0]) + " " + capitalizeFirstLetter(parts[1])).strip());
        }
        return parts[0].strip();
    }

    public static String lastNameFromString(String fullName) {
        String[] parts = fullName.trim().split("\\s+");
        StringBuilder lastName = new StringBuilder();

        int i = 1;
        boolean noLastName = false;
        if (parts[0].length() <= 1 && StringUtils.hasText(parts[1]) && !parts[1].isEmpty() ) {
            if(parts[1].length() >= 1 && parts.length <= 2) {
                noLastName = true;
            } else {
                i = 2;
            }
        }

        if(noLastName) {
            return null;
        }
        while (parts.length > i) {
            lastName.append(capitalizeFirstLetter(parts[i]));
            lastName.append(" ");
            System.out.println(lastName.toString());
            i++;
        }
        return ((lastName.toString().strip().length() != 0) ? null : lastName.toString().strip());
    }

    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return null; // Return null or empty string as is
        }

        char firstChar = str.charAt(0);

        // Check if the first character is an English alphabet letter
        if (Character.isLetter(firstChar)) {
            // Convert the first character to uppercase
            String capitalizedFirstChar = String.valueOf(firstChar).toUpperCase();
            // Concatenate with the rest of the string
            return capitalizedFirstChar + str.substring(1);
        } else {
            // If not a letter, return the original string
            return str;
        }
    }

    public static String getOnlyEntityName(String str) {
        String className;

        // Find the index of the last period
        int lastDotIndex = str.lastIndexOf('.');

        // If a period is found, get the substring after it
        if (lastDotIndex != -1) {
            className = str.substring(lastDotIndex + 1);
        } else {
            // Handle the case where there is no package name
            className = str;
        }
        return className;
    }
}
