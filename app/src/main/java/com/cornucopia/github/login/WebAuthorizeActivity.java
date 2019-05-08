package com.cornucopia.github.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cornucopia.github.R;
import com.cornucopia.github.api.ApiClient;
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

  @SuppressLint
      ("SetJavaScriptEnabled")
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web_authorize);
    ButterKnife.bind(this);
    initWebView();
  }

  @SuppressLint("SetJavaScriptEnabled") private void initWebView() {
    String url =
        "https://github.com/login/oauth/authorize?client_id=b2eff99b4041d58946d3&redirect_uri=http://localhost:8080/callback&state=lw&login=lw";
    webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    webView.clearCache(true);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.setWebViewClient(new WebViewClient() {
      @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return handleRedirectUrl(request.getUrl().toString());
      }

      @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return handleRedirectUrl(url);
      }
    });
    webView.loadUrl(url);
  }

  /**
   * 处理重定向的url.
   *
   * @param url 重定向的url地址
   * @return false, 跳转的url交给webveiw处理;true，自己处理跳转的url.
   */
  private boolean handleRedirectUrl(String url) {
    if (url.contains("code")) {
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
    ApiClient
        .getInstance()
        .getAccessToken(new Observer<ResponseBody>() {
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
                        },
            "b2eff99b4041d58946d3", "c9596586c6485063b05323872be9440d80f2ddff", code,
            "http://localhost:8080/callback", "lw");
  }

  /**
   * 存储accessToken.
   */
  private void saveAccessToken(ResponseBody responseBody) {
    try {
      String result = responseBody.string();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
