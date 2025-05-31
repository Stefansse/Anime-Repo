package com.AnimeApp.service.utils;

public class StringUtils {

    private StringUtils() {
        // private constructor to prevent instantiation
    }

    public static String prettifyGenreName(String genre) {
        if (genre == null || genre.isEmpty()) return genre;

        String[] words = genre.toLowerCase().split("_");
        StringBuilder pretty = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                pretty.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return pretty.toString().trim();
    }
}
