package com.cornucopia.github.repository;

import com.cornucopia.github.api.bean.GithubAuthRequestBean;
import com.cornucopia.github.api.bean.GithubAuthResponseBean;
import com.cornucopia.github.api.service.LoginService;
import com.cornucopia.github.common.http.retrofit.RetrofitForWebAuthorize;
import com.cornucopia.github.common.http.retrofit.RetrofitHelper;
import com.cornucopia.github.common.http.transformer.ThreadSwitchTransformer;
import io.reactivex.Observer;
import okhttp3.Credentials;
import okhttp3.ResponseBody;

/**
 * 登录数据仓库.
 *
 * @author LIUWEI <a href="mailto:misayanice@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-05-12
 */
public class LoginRepository {

  public void getAccessToken(String clientId, String clientSecret, String code,
      String redirectUri, String state, Observer<ResponseBody> observer) {
    (RetrofitHelper.getRetrofit(RetrofitForWebAuthorize.class))
        .create(LoginService.class)
        .getAccessToken(clientId, clientSecret, code, redirectUri, state)
        .compose(new ThreadSwitchTransformer<>())
        .subscribe(observer);
  }

  /**
   * 获取token.
   */
  public void getToken(String userName, String passWord,
      Observer<GithubAuthResponseBean> observer) {
    String token = Credentials.basic(userName, passWord);
    GithubAuthRequestBean githubAuthRequestBean = GithubAuthRequestBean.create();
    RetrofitHelper.getRetrofit()
        .create(LoginService.class)
        .getToken(token, githubAuthRequestBean)
        .compose(new ThreadSwitchTransformer<>())
        .subscribe(observer);
  }
}
