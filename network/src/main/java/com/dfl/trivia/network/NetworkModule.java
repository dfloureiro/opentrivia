package com.dfl.trivia.network;

import java.io.File;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Loureiro on 13/11/2017.
 *
 * init and configure retrofit, client and interceptors
 *
 * this class is implemented as a singleton to avoid multiple instances in one app run
 */

class NetworkModule {

  private final static String BASE_URL = "https://opentdb.com/";

  private static NetworkModule instance;
  private OpentdbApi opentdbApi;

  private NetworkModule() {
    setRetrofit();
  }

  static NetworkModule newInstance() {
    if (instance == null) {
      instance = new NetworkModule();
    }
    return instance;
  }

  private static Interceptor provideCacheInterceptor() {
    return chain -> {
      Response response = chain.proceed(chain.request());
      CacheControl cacheControl = new CacheControl.Builder().maxAge(2, TimeUnit.MINUTES)
          .build();
      return response.newBuilder()
          .header("Cache-Control", cacheControl.toString())
          .build();
    };
  }

  private void setRetrofit() {
    opentdbApi = new Retrofit.Builder().baseUrl(BASE_URL)
        .client(getDefaultClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(OpentdbApi.class);
  }

  private OkHttpClient getDefaultClient() {
    final int cacheMaxSize = 10 * 1024 * 1024;  // 10 MiB

    final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
    okHttpClientBuilder.addNetworkInterceptor(provideCacheInterceptor());
    okHttpClientBuilder.readTimeout(45, TimeUnit.SECONDS);
    okHttpClientBuilder.writeTimeout(45, TimeUnit.SECONDS);
    okHttpClientBuilder.cache(new Cache(new File("/"), cacheMaxSize));
    return okHttpClientBuilder.build();
  }

  OpentdbApi getOpentdbApi() {
    return opentdbApi;
  }
}
