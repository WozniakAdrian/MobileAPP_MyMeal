//Copyright 2019 Square, Inc.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package com.AdrianWozniak.mymeal.repository.api;

import android.content.Context;
import android.util.Log;

import com.AdrianWozniak.mymeal.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Class thats allow to communicate with API OpenWeatherMap.org
 * Endpoint is hit when constructor is called
 */
public class APIWeather {

    private Thread hitEndpointThread;

    //flag for response status
    private boolean isResponseEnabled = false;

    private String responseBody = null;

    private double lognitude;
    private double latitude;

    private String city;
    
    private Context context;


    public APIWeather(Context context, double longitude, double latitude) {
        this.lognitude = longitude;
        this.latitude = latitude;
        this.context = context;

        hitEndpointByLocation();
    }

    public APIWeather(Context context, String city){
        this.context = context;
        this.city = city;
        
        hitEndpointByCity();
    }

    private void hitEndpointByCity() {

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegment("data")
                .addPathSegment("2.5")
                .addPathSegment("weather")
                .addQueryParameter("q", city)
                .addQueryParameter("APPID", context.getResources().getString(R.string.APPID))
                .build();



        OkHttpClient client = new OkHttpClient();

        hitEndpointThread = new Thread(() -> {
            client.newCall(getRequest(httpUrl)).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    isResponseEnabled = false;
                    call.cancel();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    isResponseEnabled = true;
                    //get response
                    responseBody = response.body().string();
                    Log.e("MYMeal Weather", responseBody);
                }
            });
        });
        hitEndpointThread.start();
    }


    /**
     * Function to create endpoint url
     */
    private final HttpUrl getEndpointUrlByLocation() {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegment("data")
                .addPathSegment("2.5")
                .addPathSegment("weather")
                .addQueryParameter("lat", String.valueOf(latitude))
                .addQueryParameter("lon", String.valueOf(lognitude))
                .addQueryParameter("APPID", context.getResources().getString(R.string.APPID))
                .build();
        return httpUrl;
    }


    /**
     * Function to create OkHttp Request
     */
    private final Request getRequest(HttpUrl url) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        return request;
    }

    /**
     * Function to hit endpoint and get request body
     */
    private void hitEndpointByLocation() {
        hitEndpointThread = new Thread(() -> {
            OkHttpClient client = new OkHttpClient();
            client.newCall(getRequest(getEndpointUrlByLocation())).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    isResponseEnabled = false;
                    call.cancel();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    isResponseEnabled = true;
                    //get response
                    responseBody = response.body().string();
                }
            });

        });
        hitEndpointThread.start();
    }

    /**
     * Function to check if a location is already available
     *
     * @return
     */
    public boolean isResponseEnabled() {
        return isResponseEnabled;
    }

    /**
     * Function to get Response Body
     *
     * @return String or null if response is not ready
     */
    public String getResponseBody() {
        if (isResponseEnabled) {
            return responseBody;
        }
        return null;
    }
}
