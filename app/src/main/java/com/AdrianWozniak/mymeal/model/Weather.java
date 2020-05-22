package com.AdrianWozniak.mymeal.model;


/**
 * Class that represent weather from OpenWeather API
 */
public class Weather {
    private int temp;
    private int weatherId;
    private boolean isWeatherEnabled;

    public Weather() {
        isWeatherEnabled = false;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public boolean isWeatherEnabled() {
        return isWeatherEnabled;
    }

    public void setWeatherEnabled(boolean weatherEnabled) {
        isWeatherEnabled = weatherEnabled;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "temp=" + temp +
                ", weatherId=" + weatherId +
                '}';
    }
}
