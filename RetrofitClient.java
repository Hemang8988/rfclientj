package com.example.crudappjava.apis;

import com.example.crudappjava.BuildConfig;
import com.example.crudappjava.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private Retrofit retrofit;
    private OkHttpClient client;
    private Apis apiService;


    private RetrofitClient() {
        final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(interceptor);
        okHttpClientBuilder.connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(interceptor);
        }
        client = okHttpClientBuilder.build();
        retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(Apis.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }

        return instance;
    }

    public Apis getApiService() {
        return apiService;
    }

}