package com.cornucopia.github.common.http;

import android.util.Log;
import com.cornucopia.github.api.ApiService;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Okhttp,Retrofit的基本配置.
 *
 * @author LIUWEI <a href="mailto:misayanice@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-05-06
 */
public class HttpClient {
  private static final int DEFAULT_TIME_OUT = 30;
  private volatile ApiService apiService;

  public static HttpClient getInstance() {
    return SingleTon.HttpClient;
  }

  private static class SingleTon {
    private static HttpClient HttpClient = new HttpClient();
  }

  private HttpClient() {
    //配置Okhttp🏪，失败重试，日志
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
    builder.writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
    builder.readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
    builder.retryOnConnectionFailure(true);
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
        message -> Log.e("HttpClient", message));
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    builder.addInterceptor(loggingInterceptor);

    //配置Retrofit将Okhttp作为网络请求，Gson解析，Rxjava支持
    Retrofit retrofit = new Retrofit.Builder().client(builder.build())
        //.addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(ApiService.BASE_URL)
        .build();
    apiService = retrofit.create(ApiService.class);
  }

  public ApiService getApiService() {
    return apiService;
  }
}
