package com.cornucopia.github.common.http.transformer;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * 线程切换转换器，用于实现"http请求处理在io线程，回调结果切换到主线程"的通用逻辑.
 *
 * @author LIUWEI <a href="mailto:misayanice@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-05-06
 */
public class ThreadSwitchTransformer<T> implements ObservableTransformer<T, T> {

  @Override
  public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
    return upstream.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
