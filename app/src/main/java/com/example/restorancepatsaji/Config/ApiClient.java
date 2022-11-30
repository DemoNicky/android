package com.example.restorancepatsaji.Config;

import com.example.restorancepatsaji.Interface.UserService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS).addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:8080/api/").client(okHttpClient).build();
//        http://192.168.100.2:8080/api/

        return retrofit;
    }

    public static UserService getService(){
        UserService userService = getRetrofit().create(UserService.class);

        return userService;
    }
}
