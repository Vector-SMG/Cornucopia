package com.cornucopia.github.common.http.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * github web验证专门定制的retrofit.
 *
 * @author LIUWEI <a href="mailto:misayanice@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-05-12
 */
public class RetrofitForWebAuthorize extends RetrofitFactory {
  private static final String BASE_URL = "https://github.com/";

  @Override public OkHttpClient configOkHttp() {
    return null;
  }

  @Override public Retrofit configRetrofit() {
    if (configOkHttp() == null) {
      return new Retrofit.Builder()
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .baseUrl(BASE_URL)
          .build();
    }
    return new Retrofit.Builder().client(configOkHttp())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build();
  }
}
