package com.infinite.mysecurityfirst.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rajini Ragupathy on 05-06-2020.
 */
public class ApiClient {

    public static final String BASE_URL = "https://infcovid-19safe.icsblr.com:9002/contact_tracing/api/";
    public static final String COVID_URL = "https://covidtracking.com/api/v1/us/";
    public static ApiInterface getRfInstance() {

        HttpLoggingInterceptor logInter = new HttpLoggingInterceptor();
        logInter.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient mIntercepter = new OkHttpClient.Builder()
                .addInterceptor(logInter)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofitInstance = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(mIntercepter)
                .build();
        return retrofitInstance.create(ApiInterface.class);
    }

    public static ApiInterface getRfInstanceCovid() {

        HttpLoggingInterceptor logInter = new HttpLoggingInterceptor();
        logInter.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient mIntercepter = new OkHttpClient.Builder()
                .addInterceptor(logInter)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofitInstance = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(COVID_URL)
                .client(mIntercepter)
                .build();
        return retrofitInstance.create(ApiInterface.class);
    }

}
