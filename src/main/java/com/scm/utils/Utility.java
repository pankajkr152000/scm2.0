package com.scm.utils;

public class Utility {
    public static String firstNameFromString(String fullName) {

        String[] parts = fullName.trim().split("\\s+");

        return parts[0].strip();
    }

    public static String lastNameFromString(String fullName) {
        String[] parts = fullName.trim().split("\\s+");
        StringBuilder lastName = new StringBuilder();
        int i = 1;
        while (parts.length > i) {
            lastName.append(parts[i]);
            lastName.append(" ");
            System.out.println(lastName.toString());
            i++;
        }
        return lastName.toString().strip();
    }
}
