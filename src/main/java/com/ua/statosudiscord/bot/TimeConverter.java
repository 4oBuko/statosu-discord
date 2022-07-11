package com.ua.statosudiscord.bot;

public class TimeConverter {
    public static String convertMinToHours(int min) {
        String result = "";
        result += (min >= 60) ? (min - (min % 60)) / 60 +" hr " : "";
        result += (min % 60 > 0) ? min%60 + " min" : "";
        return result.trim();
    }
}
