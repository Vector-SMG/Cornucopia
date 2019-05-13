package com.cornucopia.github.common.utils;

import android.content.Context;
import com.cornucopia.github.R;
import java.util.Arrays;
import java.util.List;

/**
 * 常量类.
 *
 * @author LIUWEI <a href="mailto:misayanice@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-05-09
 */
public class Constants {
  public static String accessToken;
  public static String token_type;

  /**
   * 获取client_id.
   */
  public static String getClientID(Context context) {
    return context.getString(R.string.client_id);
  }

  /**
   * 获取client_secret.
   */
  public static String getClientSecret(Context context) {
    return context.getString(R.string.client_secret);
  }

  /**
   * 获取note.
   */
  public static String getNote(Context context) {
    return context.getString(R.string.note);
  }

  /**
   * 获取scopes.
   */
  public static List<String> getScopes(Context context) {
    return Arrays.asList(context.getResources().getStringArray(R.array.repos));
  }
}
