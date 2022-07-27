package com.ua.statosudiscord.bot;

import com.ua.statosudiscord.utils.TimeConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeConverterTest {

    @Test
    public void zeroSec() {
        assertEquals("0 min", TimeConverter.convertSecondsToString(0));
    }

    @Test
    public void onlyMinutes() {
        assertEquals("59 min",TimeConverter.convertSecondsToString(60*59));
    }

    @Test
    public void oneHour() {
        assertEquals("1 hr 0 min", TimeConverter.convertSecondsToString(60*60));
    }

    @Test
    public void hoursAndMinutes1() {
        assertEquals("2 hr 8 min", TimeConverter.convertSecondsToString(2*3600 + 8*60));
    }

    @Test
    public void hoursAndMinutes2() {
        assertEquals("7 hr 33 min", TimeConverter.convertSecondsToString(7*3600 + 33*60 + 59));
    }

    @Test
    public void hoursAndMinutes3() {
        assertEquals("1 hr 1 min", TimeConverter.convertSecondsToString(3600 + 60));
    }

    @Test
    public void bigValue() {
        assertEquals("400 hr 44 min", TimeConverter.convertSecondsToString(400*3600 + 44*60));
    }

    @Test
    public void veryBigValue() {
        assertEquals("1234 hr 20 min", TimeConverter.convertSecondsToString(1234*3600 + 20*60));
    }
}