package com.AdrianWozniak.mymeal.service;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class DateTimeServiceTest {

    @Test
    public void getDayOfWeekName() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        assertEquals(simpleDateFormat.format(date), ServiceDateTime.getDayOfWeekName());
    }
}