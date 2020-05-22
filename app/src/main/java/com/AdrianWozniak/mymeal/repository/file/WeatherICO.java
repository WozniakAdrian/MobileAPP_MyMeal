package com.AdrianWozniak.mymeal.repository.file;


import com.AdrianWozniak.mymeal.R;

import java.util.HashMap;


/**
 * Class for map icons to id
 */
public final class WeatherICO {

    private static HashMap<Integer, Integer> MAP;

    private WeatherICO() {
        if(MAP == null || MAP.isEmpty()){
            MAP = new HashMap<>();
            addItems();
        }
    }

    private final static void addItems(){
        MAP.put(200, R.drawable.weather_thunderstorm);
        MAP.put(201, R.drawable.weather_thunderstorm);
        MAP.put(202, R.drawable.weather_thunderstorm);
        MAP.put(210, R.drawable.weather_thunderstorm);
        MAP.put(211, R.drawable.weather_thunderstorm);
        MAP.put(212, R.drawable.weather_thunderstorm);
        MAP.put(221, R.drawable.weather_thunderstorm);
        MAP.put(230, R.drawable.weather_thunderstorm);
        MAP.put(231, R.drawable.weather_thunderstorm);
        MAP.put(232, R.drawable.weather_thunderstorm);

        MAP.put(300,R.drawable.weather_shower_rain);
        MAP.put(301,R.drawable.weather_shower_rain);
        MAP.put(302,R.drawable.weather_shower_rain);
        MAP.put(310,R.drawable.weather_shower_rain);
        MAP.put(311,R.drawable.weather_shower_rain);
        MAP.put(312,R.drawable.weather_shower_rain);
        MAP.put(313,R.drawable.weather_shower_rain);
        MAP.put(314,R.drawable.weather_shower_rain);
        MAP.put(321,R.drawable.weather_shower_rain);

        MAP.put(500,R.drawable.weather_rain);
        MAP.put(501,R.drawable.weather_rain);
        MAP.put(502,R.drawable.weather_rain);
        MAP.put(503,R.drawable.weather_rain);
        MAP.put(504,R.drawable.weather_rain);
        MAP.put(511,R.drawable.weather_snow);
        MAP.put(520,R.drawable.weather_shower_rain);
        MAP.put(521,R.drawable.weather_shower_rain);
        MAP.put(522,R.drawable.weather_shower_rain);
        MAP.put(530,R.drawable.weather_shower_rain);

        MAP.put(600,R.drawable.weather_snow);
        MAP.put(601,R.drawable.weather_snow);
        MAP.put(602,R.drawable.weather_snow);
        MAP.put(611,R.drawable.weather_snow);
        MAP.put(612,R.drawable.weather_snow);
        MAP.put(613,R.drawable.weather_snow);
        MAP.put(615,R.drawable.weather_snow);
        MAP.put(616,R.drawable.weather_snow);
        MAP.put(620,R.drawable.weather_snow);
        MAP.put(621,R.drawable.weather_snow);
        MAP.put(622,R.drawable.weather_snow);

        MAP.put(701,R.drawable.weather_mist);
        MAP.put(711,R.drawable.weather_mist);
        MAP.put(721,R.drawable.weather_mist);
        MAP.put(731,R.drawable.weather_mist);
        MAP.put(741,R.drawable.weather_mist);
        MAP.put(751,R.drawable.weather_mist);
        MAP.put(761,R.drawable.weather_mist);
        MAP.put(762,R.drawable.weather_mist);
        MAP.put(771,R.drawable.weather_mist);
        MAP.put(781,R.drawable.weather_mist);

        MAP.put(800,R.drawable.weather_clear_sky);

        MAP.put(801,R.drawable.weather_few_clouds);
        MAP.put(802,R.drawable.weather_scattered_clouds);
        MAP.put(803,R.drawable.weather_broken_clouds);
        MAP.put(804,R.drawable.weather_broken_clouds);
    }

    /**
     * Function to get whole map of icons
     * @return HashMap
     */
    public final static HashMap<Integer, Integer> getMap(){
        new WeatherICO();
        return MAP;
    }

    /**
     * Function to get single icon
     * @param key int
     * @return Integer
     */
    public final static Integer getIcoByKey(int key) throws IndexOutOfBoundsException{
        new WeatherICO();
        if (MAP.keySet().stream().anyMatch(mapKey -> mapKey == key)) {
            return MAP.get(key);

        } else
            throw new IndexOutOfBoundsException("Key is out of range");
    }
}
