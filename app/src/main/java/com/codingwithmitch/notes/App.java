package com.codingwithmitch.notes;

import android.app.Application;
import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static JsonPlaceHolderApi jsonPlaceHolderApi;
    private Retrofit retrofit;


    @Override
    public void onCreate() {
        super.onCreate();
        //mContext = this;

        retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    }

    public static JsonPlaceHolderApi getApi() {
        return jsonPlaceHolderApi;
    }
}
