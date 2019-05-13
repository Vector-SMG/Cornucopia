package com.cornucopia.github.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cornucopia.github.repository.LoginRepository;
import com.cornucopia.github.ui.MainActivity;
import com.cornucopia.github.R;
import com.cornucopia.github.common.utils.Constants;
import com.cornucopia.github.common.utils.QMUIStatusBarHelper;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.io.IOException;
import okhttp3.ResponseBody;

/**
 * github网页验证登录.
 *
 * @author LIUWEI <a href="mailto:misayanice@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-05-07
 */
public class WebAuthorizeActivity extends AppCompatActivity {
  @BindView(R.id.web_view)
  WebView webView;

  private LoginRepository loginRepository;

  @SuppressLint
      ("SetJavaScriptEnabled")
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web_authorize);
    QMUIStatusBarHelper.setStatusBarLightMode(this);
    ButterKnife.bind(this);
    loginRepository = new LoginRepository();
    initWebView();
  }

  @SuppressLint("SetJavaScriptEnabled") private void initWebView() {
    String url =
        "https://github.com/login/oauth/authorize?client_id=b2eff99b4041d58946d3&redirect_uri=http://localhost:8080/callback&state=lw&login=lw&scope=user%20public_repo";
    webView.getSettings().setJavaScriptEnabled(true);
    webView.setWebViewClient(new WebViewClient() {
      @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return handleRedirectUrl(request);
      }
    });
    webView.loadUrl(url);
  }

  /**
   * 处理重定向的url.
   *
   * @param request request类
   * @return false, 跳转的url交给webveiw处理;true，自己处理跳转的url.
   */
  private boolean handleRedirectUrl(WebResourceRequest request) {
    if (request.getUrl().toString().contains("code")) {
      String code = request.getUrl().getQueryParameter("code");
      getAccessToken(code);
      return true;
    }
    return false;
  }

  /**
   * 获取AccessToken.
   *
   * @param code 特殊code
   */
  private void getAccessToken(String code) {
    loginRepository
        .getAccessToken(
            "b2eff99b4041d58946d3", "c9596586c6485063b05323872be9440d80f2ddff", code,
            "http://localhost:8080/callback", "lw", new Observer<ResponseBody>() {
              @Override public void onSubscribe(Disposable d) {
              }

              @Override public void onNext(ResponseBody responseBody) {
                saveAccessToken(responseBody);
              }

              @Override public void onError(Throwable e) {
                Log.e("请求失败", e.getMessage());
              }

              @Override
              public void onComplete() {
              }
            });
  }

  /**
   * 存储accessToken.
   */
  private void saveAccessToken(ResponseBody responseBody) {
    try {
      //access_token=15c641debcc2d3b507603b3882cd4fb954c77550&scope=&token_type=bearer
      String result = responseBody.string();
      if (result.contains("access_token")) {
        String params[] = result.split("&");
        if (params.length > 0) {
          Constants.accessToken = params[0].split("=")[1];
          Log.e("access_token", result);
          Constants.token_type = params[2].split("=")[1];
          Intent intent = new Intent(this, MainActivity.class);
          startActivity(intent);
          finish();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
