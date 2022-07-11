package com.ua.statosudiscord.bot;

public class TimeConverter {
    public static String convertSecondsToString(int sec) {
        String result = "";
        int hours = (sec - (sec % 3600)) / 3600;
        int min = (sec - hours * 3600 - sec % 60) / 60;
        result += (hours > 0) ? hours + " hr " : "";
        result += (min >= 0) ? min + " min" : "";
        return result.trim();
    }
}
