package com.AdrianWozniak.mymeal.repository.file;

import com.AdrianWozniak.mymeal.R;

import org.junit.Test;

import static org.junit.Assert.*;

public class WeatherICOTest {

    @Test
    public void getMapIsNotNull() {

        //map is not null
        assertNotNull(WeatherICO.getMap());
    }

    @Test
    public void getMapSizeIs55() {
        //map is size of 55 elements
        int size = 55;
        assertEquals(size, WeatherICO.getMap().size());
    }

    @Test
    public void getIcoByKey() {
        assertEquals((Integer)R.drawable.weather_mist, WeatherICO.getIcoByKey(701));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getIcoByKeyOutOfRange() {
        WeatherICO.getIcoByKey(1);
    }


}