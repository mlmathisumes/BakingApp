package com.example.bakingapp.utils;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    public static final String TAG = RetrofitInstance.class.getSimpleName();

    /*
    * Create an instance of Retrofit object
     */
    public static Retrofit getRetrofitInstance(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                    .build();
            Log.d(TAG, "Creating new Retrofit instance");
        }

        return retrofit;
    }
}
