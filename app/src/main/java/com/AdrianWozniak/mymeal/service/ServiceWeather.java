package com.AdrianWozniak.mymeal.service;

import android.content.Context;

import com.AdrianWozniak.mymeal.model.Weather;
import com.AdrianWozniak.mymeal.repository.api.APIWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ServiceWeather {

    private Weather weather;
    private APIWeather apiWeather;


    public ServiceWeather(Context context, String city) {
        this.weather = new Weather();
        apiWeather = new APIWeather(context, city);
        getResponseFromApi();
    }

    private void getResponseFromApi() {
        new Thread(() -> {
            while (true) {
                if (apiWeather.isResponseEnabled()) {
                    getWeatherFromJSON(apiWeather.getResponseBody());
                    break;
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Function parse json into weather obj
     * @param responseBody
     * @return
     */
    private Weather getWeatherFromJSON(String responseBody) throws NullPointerException {

        try {
            //parse json to object
            JSONObject jsonObject = new JSONObject(responseBody);

            //get weather id
            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            for (int i = 0; i < weatherArray.length(); i++) {
                //set weather id
                weather.setWeatherId(Integer.parseInt(weatherArray.getJSONObject(i).getString("id")));
            }
            //get temperature
            JSONObject tempObject = jsonObject.getJSONObject("main");
            String temp = tempObject.getString("temp");

            //concert kelwin to celcius and set temp
            weather.setTemp((int) Math.round(Double.parseDouble(temp) - 273.15));

            System.out.println(weather.toString());


            weather.setWeatherEnabled(true);

            return weather;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }

    /**
     * Retruen true or false if weather data is enabled
     * @return
     */
    public boolean isWeatherEnabled() {
        return weather.isWeatherEnabled();
    }

    /**
     * get Weather, first check if weather is enabled [isWeatherEnabled()];
     * @return
     */
    public Weather getWeather(){
        return weather;
    }
}
