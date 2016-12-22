package com.ground0.repository;

import android.util.Log;
import java.io.IOException;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by zer0 on 22/12/16.
 */

public class HttpResponseStatusOperator<T> implements Observable.Operator<T, Response<T>> {

  CustomObjectMapper objectMapper;

  public HttpResponseStatusOperator(CustomObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override public Subscriber<? super Response<T>> call(final Subscriber<? super T> subscriber) {
    return new Subscriber<Response<T>>() {
      @Override public void onCompleted() {
        if (!subscriber.isUnsubscribed()) subscriber.onCompleted();
      }

      @Override public void onError(Throwable e) {
        if (subscriber.isUnsubscribed()) return;
        subscriber.onError(e);
        subscriber.onCompleted();
      }

      @Override public void onNext(Response<T> response) {
        if (subscriber.isUnsubscribed()) return;
        if (response != null) Log.d(getClass().getSimpleName(), response.body().toString());
        if (response.isSuccessful()) {
          subscriber.onNext(response.body());
        } else {
          try {
            subscriber.onError(new Throwable(response.errorBody().string()));
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    };
  }
}