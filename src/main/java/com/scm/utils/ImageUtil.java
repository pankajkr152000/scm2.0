package com.scm.utils;

import org.springframework.stereotype.Component;

@Component
public class ImageUtil {

    public String buildFileName(String fullName, long sequence, String originalName) {

        String clean = fullName.replaceAll("\\s+", "").toUpperCase();
        String prefix = clean.length() >= 5 ? clean.substring(0, 5) : clean;

        String extension = originalName.substring(originalName.lastIndexOf('.') + 1);

        String filename = prefix + "_" + String.format("%06d", sequence) + "." + extension;

        return filename;
    }
}

