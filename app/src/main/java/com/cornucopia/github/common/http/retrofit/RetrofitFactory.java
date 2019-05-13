package com.cornucopia.github.common.http.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * retrofit实例的工厂.
 *
 * @author LIUWEI <a href="mailto:misayanice@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-05-12
 */
public abstract class RetrofitFactory {

  public abstract OkHttpClient configOkHttp();

  public abstract Retrofit configRetrofit();
}
