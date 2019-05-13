package com.cornucopia.github.common.http.retrofit;

import android.util.Log;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 标准的retrofit定制包括:okhttp作为网络请求客户端，gson转换器，rxjava适配器等.
 *
 * @author LIUWEI <a href="mailto:misayanice@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-05-12
 */
public class StandardRetrofitConfig extends RetrofitFactory {
  private static final int DEFAULT_TIME_OUT = 30;
  private static final String BASE_URL = "https://api.github.com/";

  @Override public OkHttpClient configOkHttp() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
    builder.writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
    builder.readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
    builder.retryOnConnectionFailure(true);
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
        message -> Log.e("HttpClient", message));
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    builder.addInterceptor(loggingInterceptor);
    return builder.build();
  }

  @Override public Retrofit configRetrofit() {
    return new Retrofit.Builder().client(configOkHttp())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build();
  }
}
