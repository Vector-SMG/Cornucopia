package com.cornucopia.github.common.http.retrofit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import retrofit2.Retrofit;

/**
 * Retrofit实例创建辅助类，目的可以定制各种不同需求的retrofit.
 *
 * @author LIUWEI <a href="mailto:misayanice@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-05-12
 */
public class RetrofitHelper {

  private static final Map<String, Object> RETROFIT_CACHE = new HashMap<>();

  /**
   * 获取retrofit.
   */
  public static Retrofit getRetrofit() {
    Class clz = StandardRetrofitConfig.class;
    return getRetrofit(clz);
  }

  /**
   * 获取retrofit.
   */
  public static <T extends RetrofitFactory> Retrofit getRetrofit(Class<T> clz) {
    RetrofitFactory retrofitFactory;
    synchronized (RETROFIT_CACHE) {
      if (clz == null) {
        //todo 待进行类型检测并复习反射以及虚拟机
        clz = (Class<T>) StandardRetrofitConfig.class;
      }
      retrofitFactory = (RetrofitFactory) RETROFIT_CACHE.get(clz.getName());
      if (retrofitFactory == null) {
        try {
          retrofitFactory = (RetrofitFactory) Class.forName(clz.getName()).newInstance();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return  Objects.requireNonNull(retrofitFactory).configRetrofit();
  }
}
