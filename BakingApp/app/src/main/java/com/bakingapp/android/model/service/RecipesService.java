package com.bakingapp.android.model.service;


import com.bakingapp.android.data.Recipe;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

//import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

public class RecipesService {

    private final HttpLoggingInterceptor httpLoggingInterceptor;
    private final ServiceConfig serviceConfig;
    private final StockServiceInterface service;

    interface StockServiceInterface {
        @GET("topher/2017/May/59121517_baking/baking.json")
        Observable<List<Recipe>> getRecipes();
    }

    public RecipesService(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
        httpLoggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        service = buildService();
    }

    public Observable<List<Recipe>> getRecipes() {
        return service.getRecipes()
                .subscribeOn(Schedulers.io());
    }

    private StockServiceInterface buildService() {
        return getDefaultRetrofitBuilder().build().create(StockServiceInterface.class);
    }

    private Retrofit.Builder getDefaultRetrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(serviceConfig.getBaseServiceUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClientBuilder().build())
                .addConverterFactory(GsonConverterFactory.create());
    }

    private OkHttpClient.Builder getOkHttpClientBuilder() {
        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(chain -> {
                    Request request = chain
                            .request()
                            .newBuilder()
//                            .addHeader(CONTENT_TYPE, "application/json")
                            .build();
                    return chain.proceed(request);
                });
    }

}
