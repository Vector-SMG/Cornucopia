package com.cornucopia.github.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * .
 *
 * @author LIUWEI <a href="mailto:misayanice@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-05-06
 */
public interface ApiService {

  String BASE_URL = "https://github.com/";

  /**
   * 登录.
   *
   * @return 字符串
   */
  @FormUrlEncoded
  @POST("login/oauth/access_token")
  Observable<ResponseBody> getAccessToken(@Field("client_id") String clientId,
      @Field("client_secret") String clientSecret,
      @Field("code") String code,
      @Field("redirect_uri") String redirectUri,
      @Field("state") String state
  );
}
