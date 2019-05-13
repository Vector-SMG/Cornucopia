package com.cornucopia.github.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloCallback;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.fetcher.ApolloResponseFetchers;
import com.cornucopia.github.R;
import com.cornucopia.github.common.utils.Constants;
import com.cornucopia.github.common.utils.QMUIStatusBarHelper;
import com.github.cornucopia.LoginQuery;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;

/**
 * 主界面.
 *
 * @author LIUWEI <a href="mailto:misayanice@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-05-02
 */
public class MainActivity extends AppCompatActivity {

  Handler uiHandler = new Handler(Looper.getMainLooper());

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    QMUIStatusBarHelper.setStatusBarLightMode(this);
    init();
  }

  private void init() {
    LoginQuery loginQuery = LoginQuery.builder().build();

    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.retryOnConnectionFailure(true);
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
        message -> Log.e("HttpClient", message));
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    builder.addInterceptor(loggingInterceptor);
    builder.addInterceptor(new Interceptor() {
      @Override public Response intercept(Chain chain) throws IOException {
        Request newRequest = chain
            .request()
            .newBuilder()
            .addHeader("Authorization", "bearer " + Constants.accessToken)
            .build();
        return chain.proceed(newRequest);
      }
    });

    ApolloClient apolloClient = ApolloClient.builder()
        .serverUrl("https://api.github.com/graphql")
        .okHttpClient(builder.build())
        .build();
    ApolloCall<LoginQuery.Data> call =
        apolloClient.query(loginQuery).responseFetcher(ApolloResponseFetchers.NETWORK_ONLY);
    call.enqueue(dataCallback);
  }

  private ApolloCall.Callback<LoginQuery.Data> dataCallback
      = new ApolloCallback<>(new ApolloCall.Callback<LoginQuery.Data>() {
    @Override public void onResponse(@NotNull
        com.apollographql.apollo.api.Response<LoginQuery.Data> response) {
      Log.e("请求成功", response.toString());
    }

    @Override public void onFailure(@NotNull ApolloException e) {
      Log.e("请求失败", e.getMessage());
    }
  }, uiHandler);
}
