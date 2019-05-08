package com.cornucopia.github.api;

import com.cornucopia.github.common.http.HttpClient;
import com.cornucopia.github.common.http.core.transformer.ThreadSwitchTransformer;
import io.reactivex.Observer;
import okhttp3.ResponseBody;

/**
 * api请求端.
 *
 * @author LIUWEI <a href="mailto:misayanice@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-05-06
 */
public class ApiClient {
  private volatile ApiService apiService;

  private ApiClient() {
    this.apiService = HttpClient.getInstance().getApiService();
  }

  private static class SingleTon {
    private static final ApiClient INSTANCE = new ApiClient();
  }

  public static ApiClient getInstance() {
    return SingleTon.INSTANCE;
  }

  /**
   * 登录.
   *
   * @param observer 回调
   */
  public void getAccessToken(Observer<ResponseBody> observer, String clientId, String clientSecret,
      String code, String redirectUri,
      String state) {
    apiService.getAccessToken(clientId, clientSecret, code, redirectUri, state)
        .compose(new ThreadSwitchTransformer<ResponseBody>())
        .subscribe(observer);
  }
}
