package com.cornucopia.github.api.service;

import com.cornucopia.github.api.bean.GithubAuthRequestBean;
import com.cornucopia.github.api.bean.GithubAuthResponseBean;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * github登录.
 *
 * @author LIUWEI <a href="mailto:misayanice@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-05-06
 */
public interface LoginService {

  /**
   * 网页认证，获取acces_token.
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

  /**
   * basic认证，获取token等个人信息.
   * @see GithubAuthResponseBean
   */
  @POST("authorizations")
  @Headers("Accept: application/json")
  Observable<GithubAuthResponseBean> getToken(@Header("Authorization") String authHeader,
      @Body GithubAuthRequestBean githubAuthRequestBean);
}
