package com.cornucopia.github.api.bean;

import android.content.Context;
import com.cornucopia.github.R;
import com.cornucopia.github.common.utils.Constants;
import java.util.Arrays;
import java.util.List;

/**
 * .
 *
 * @author LIUWEI <a href="mailto:misayanice@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-05-11
 */
public class GithubAuthRequestBean {
  private List<String> scopes;
  private String note;
  private String note_url;
  private String client_id;
  private String client_secret;
  private String fingerprint;

  /**
   * 构建github basic验证所需bean类.
   */
  public static GithubAuthRequestBean create(Context context) {
    GithubAuthRequestBean githubAuthRequestBean = new GithubAuthRequestBean();
    githubAuthRequestBean.scopes = Constants.getScopes(context);
    githubAuthRequestBean.note = Constants.getNote(context);
    githubAuthRequestBean.client_id = Constants.getClientID(context);
    githubAuthRequestBean.client_secret = Constants.getClientSecret(context);
    return githubAuthRequestBean;
  }
}
