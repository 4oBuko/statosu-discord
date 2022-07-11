package com.ua.statosudiscord.bot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeConverterTest {

    @Test
    public void zeroMin() {
        assertEquals("", TimeConverter.convertMinToHours(0));
    }

    @Test
    public void onlyMinutes() {
        assertEquals("59 min",TimeConverter.convertMinToHours(59));
    }

    @Test
    public void oneHour() {
        assertEquals("1 hr", TimeConverter.convertMinToHours(60));
    }

    @Test
    public void hoursAndMinutes1() {
        assertEquals("2 hr 8 min", TimeConverter.convertMinToHours(128));
    }

    @Test
    public void hoursAndMinutes2() {
        assertEquals("7 hr 33 min", TimeConverter.convertMinToHours(453));
    }

    @Test
    public void hoursAndMinutes3() {
        assertEquals("1 hr 1 min", TimeConverter.convertMinToHours(61));
    }

    @Test
    public void oneThousandMinutes() {
        assertEquals("16 hr 40 min", TimeConverter.convertMinToHours(1000));
    }

    @Test
    public void fiveThousandMinutes() {
        assertEquals("83 hr 20 min", TimeConverter.convertMinToHours(5000));
    }
}