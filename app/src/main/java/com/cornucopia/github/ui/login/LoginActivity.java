package com.cornucopia.github.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.cornucopia.github.R;
import com.cornucopia.github.api.bean.GithubAuthResponseBean;
import com.cornucopia.github.common.utils.Constants;
import com.cornucopia.github.common.utils.QMUIStatusBarHelper;
import com.cornucopia.github.repository.LoginRepository;
import com.cornucopia.github.ui.MainActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 登录界面.
 *
 * @author LIUWEI <a href="mailto:misayanice@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-05-02
 */
public class LoginActivity extends AppCompatActivity {
  @BindView(R.id.github_account_tv) EditText githubAccountTv;
  @BindView(R.id.github_password_tv) EditText githubPasswordTv;

  private LoginRepository loginRepository;

  @OnClick
      (R.id.github_login_btn) void login() {
    String username = githubAccountTv.getText().toString();
    String password = githubPasswordTv.getText().toString();
    loginRepository.getToken(username, password, new Observer<GithubAuthResponseBean>() {
      @Override public void onSubscribe(Disposable d) {

      }

      @Override public void onNext(GithubAuthResponseBean githubAuthResponseBean) {
        Constants.accessToken = githubAuthResponseBean.getToken();
        jump();
      }

      @Override public void onError(Throwable e) {
        Log.e("请求失败", e.getMessage());
      }

      @Override public void onComplete() {

      }
    });
  }

  private void jump() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
    finish();
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    QMUIStatusBarHelper.setStatusBarLightMode(this);
    ButterKnife.bind(this);
    loginRepository = new LoginRepository();
  }
}
