//Copyright 2019 Square, Inc.
//
//        Licensed under the Apache License, Version 2.0 (the "License");
//        you may not use this file except in compliance with the License.
//        You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
//        Unless required by applicable law or agreed to in writing, software
//        distributed under the License is distributed on an "AS IS" BASIS,
//        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//        See the License for the specific language governing permissions and
//        limitations under the License.

package com.AdrianWozniak.mymeal.repository.api;

import android.content.Context;
import android.util.Log;

import com.AdrianWozniak.mymeal.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 *  Class thats allow to communicate with API Nutritionix
 *  Endpoint is hit when constructor is called
 */
public class APINutritionix {

    private Context context;

    private String query;

    private boolean isResponseEnabled = false;

    private String responseBody = null;

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public APINutritionix(Context context, String query) {
        this.context = context;
        this.query = query;

        hitEndpoint();
    }

    /**
     * Function to hit endpoint and get request body
     */
    private void hitEndpoint() {
        new Thread(()->{
            OkHttpClient client = new OkHttpClient();
            client.newCall(getRequest()).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    isResponseEnabled = false;
                    call.cancel();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    isResponseEnabled = true;
                    responseBody = response.body().string();
                    Log.d("JSON BODY", responseBody);
                }
            });

        }).start();
    }

    /**
     * Function to create OkHttp Request
     */
    private Request getRequest() {
        final Request request = new Request.Builder()
                .url(getEndpointUrl())
                .post(getBody())
                .build();
        return request;
    }

    private RequestBody getBody() {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("query", this.query);
            jsonBody.put("timezone", "US/Eastern");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, String.valueOf(jsonBody));

        return body;
    }


    /**
     *  Function to create endpoint url
     */
    private HttpUrl getEndpointUrl() {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("trackapi.nutritionix.com")
                .addPathSegment("v2")
                .addPathSegment("natural")
                .addPathSegment("nutrients")
                .addQueryParameter("x-app-id", context.getResources().getString(R.string.x_app_id))
                .addQueryParameter("x-app-key", context.getResources().getString(R.string.x_app_key))
                .addQueryParameter("x-remote-user-id", context.getResources().getString(R.string.x_remote_user_id))
                .build();
        System.out.println(url);

        return url;
    }


    /**
     * Function to check if a location is already available
     * @return
     */
    public boolean isResponseEnabled() {
        return isResponseEnabled;
    }

    /**
     * Function to get Response Body
     * @return String or null if response is not ready
     */
    public String getResponseBody() {
        if(isResponseEnabled){
            return responseBody;
        }
        else return null;
    }

}





















































