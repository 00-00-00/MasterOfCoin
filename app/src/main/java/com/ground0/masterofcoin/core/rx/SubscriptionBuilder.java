package com.ground0.masterofcoin.core.rx;

import android.util.Log;
import rx.Subscriber;

/**
 * Created by zer0 on 23/12/16.
 */
public class SubscriptionBuilder {

  private static SubscriptionBuilder ourInstance = new SubscriptionBuilder();

  public static SubscriptionBuilder getInstance() {
    return ourInstance;
  }

  private SubscriptionBuilder() {
  }

  public Builder builder() {
    return new Builder();
  }

  public class Builder {

    ApiSubcription apiSubcription = new ApiSubcription();

    public Builder onError(OnError onError) {
      apiSubcription.onError = onError;
      return this;
    }

    public Builder onNext(OnNext onNext) {
      apiSubcription.onNext = onNext;
      return this;
    }

    public Builder onComplete(OnComplete onComplete) {
      apiSubcription.onComplete = onComplete;
      return this;
    }

    public Builder setUnsubscribeOnComplete(boolean flag) {
      apiSubcription.unSubscribeOnComplete = flag;
      return this;
    }

    public ApiSubcription build() {
      return this.apiSubcription;
    }
  }

  public class ApiSubcription<T> extends Subscriber<T> {

    protected boolean unSubscribeOnComplete = false;
    protected OnNext onNext;
    protected OnError onError;
    protected OnComplete onComplete;

    @Override public void onCompleted() {
      if (unSubscribeOnComplete) unsubscribe();
      if (onComplete != null) {
        onComplete.onComplete();
      }
    }

    @Override public void onError(Throwable e) {
      Log.e(getClass().getSimpleName(), "Error in subscription");
      if (onError != null) {
        onError.onError(e);
      }
      if (onComplete != null) {
        onComplete.onComplete();
      }
    }

    @Override public void onNext(T t) {
      if (onNext != null) {
        onNext.onNext(t);
      }
    }
  }
}
