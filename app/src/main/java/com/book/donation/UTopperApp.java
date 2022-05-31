package com.book.donation;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.book.donation.apicalls.api.ApiService;
import com.book.donation.apicalls.api.CustomInterceptor;
import com.book.donation.apicalls.api.ApiConstants;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UTopperApp extends MultiDexApplication {

    private static UTopperApp mInstance;
    private ApiService apiService, apiService1;
    String androidId = "",pushRegId = "";

    public UTopperApp() {
        super();
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static synchronized UTopperApp getInstance() {
        //return mInstance;
        // If instance is not available, create it. If available, reuse and return the object.
        if (mInstance == null)
            mInstance = new UTopperApp();
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        FirebaseApp.initializeApp(this);
        MultiDex.install(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        /*
        create retrofit service forapi call
        */
        createApiService();
        //testService();

    }

    public ApiService createApiService() {
        Gson gson = new GsonBuilder().create();
        File httpCacheDirectory = new File(getCacheDir(), "cache_file");
        Cache cache = new Cache(httpCacheDirectory, 20 * 1024 * 1024);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .connectionPool(new ConnectionPool(0, 5 * 60 * 1000, TimeUnit.SECONDS))
                .addInterceptor(new CustomInterceptor(getInstance(), Locale.getDefault().getLanguage(), "1"))
                .cache(cache)
                .build();

        //init retrofit
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient)
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(ApiService.class);
        return apiService;
    }

    /*public ApiService testService() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        File httpCacheDirectory = new File(getCacheDir(), "cache_file");
        Cache cache = new Cache(httpCacheDirectory, 20 * 1024 * 1024);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .connectionPool(new ConnectionPool(0, 5 * 60 * 1000, TimeUnit.SECONDS))
                .addInterceptor(new CustomInterceptor(getInstance(), Locale.getDefault().getLanguage(), "1"))
                .cache(cache)
                .build();

        //init retrofit
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient)
                .baseUrl(ApiConstants.TEST_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService1 = retrofit.create(ApiService.class);
        return apiService1;
    }*/

    public ApiService getApiService() {
        return apiService;
    }

    public ApiService getTestService() {
        return apiService1;
    }
}
