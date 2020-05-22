package com.AdrianWozniak.mymeal.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WeatherTest {

    private Weather weather;

    @Before
    public void setUp() throws Exception {
        weather = new Weather();
    }

    @Test
    public void setTemp() {
        weather.setTemp(30);
        assertEquals(30, weather.getTemp());
    }

    @Test
    public void setWeatherId() {
        weather.setWeatherId(301);
        assertEquals(301, weather.getWeatherId());
    }

    @Test
    public void setWeatherEnabled() {
        weather.setWeatherEnabled(true);
        assertTrue(weather.isWeatherEnabled());
    }

    @Test
    public void weatherExist(){
        assertNotNull(weather);
    }
}