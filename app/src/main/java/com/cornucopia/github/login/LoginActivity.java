package com.cornucopia.github.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.cornucopia.github.R;
import com.cornucopia.github.api.ApiClient;
import com.cornucopia.github.common.utils.QMUIStatusBarHelper;
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

  @OnClick
      (R.id.github_login_btn) void login() {
    String username = githubAccountTv.getText().toString();
    String password = githubPasswordTv.getText().toString();
    Intent intent=new Intent(this,WebAuthorizeActivity.class);
    startActivity(intent);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    QMUIStatusBarHelper.setStatusBarLightMode(this);
    ButterKnife.bind(this);
  }
}
