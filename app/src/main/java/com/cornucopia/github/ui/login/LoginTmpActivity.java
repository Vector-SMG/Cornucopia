package com.cornucopia.github.ui.login;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.cornucopia.github.R;
import com.cornucopia.github.common.utils.QMUIStatusBarHelper;

/**
 * 登录临时.
 *
 * @author LIUWEI <a href="mailto:misayanice@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-05-08
 */
public class LoginTmpActivity extends AppCompatActivity {

  @OnClick
      (R.id.github_login_btn) void login() {
    Intent intent = new Intent(this, WebAuthorizeActivity.class);
    startActivity(intent);
    finish();
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_tmp);
    QMUIStatusBarHelper.setStatusBarLightMode(this);
    ButterKnife.bind(this);
  }
}
