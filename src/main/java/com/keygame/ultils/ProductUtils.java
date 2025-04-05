package com.keygame.ultils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class ProductUtils {
    public static String convertToURLFormat(String input) {
        String temp = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String nonDiacriticText = pattern.matcher(temp).replaceAll("");
        return nonDiacriticText.replaceAll("\\s+", "-").toLowerCase();
    }
}
