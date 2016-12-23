package com.ground0.masterofcoin.core.baseViewModel;

import com.ground0.masterofcoin.core.baseComponents.BaseActivity;
import com.ground0.masterofcoin.core.event.Event;
import com.ground0.masterofcoin.core.rx.SubscriptionBuilder;
import java.lang.ref.WeakReference;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zer0 on 22/12/16.
 */

public abstract class BaseActivityViewModel<T extends BaseActivity> implements ViewModel {

  WeakReference<T> activity;
  SubscriptionBuilder subscriptionBuilder = SubscriptionBuilder.getInstance();

  public void registerActivity(T activity) {
    this.activity = new WeakReference<T>(activity);
    afterRegister();
  }

  public void afterRegister() {

  }

  public T getActivity() {
    return activity.get();
  }

  public SubscriptionBuilder getSubscriptionBuilder() {
    return subscriptionBuilder;
  }

  public BehaviorSubject<Event> getAppBehaviourBus() {
    if (getActivity() == null) return null;
    return getActivity().getAppBehaviourSubject();
  }

  public PublishSubject<Event> getAppPublishBus() {
    if (getActivity() == null) return null;
    return getActivity().getAppPublishSubject();
  }

  public CompositeSubscription getCompositeSubscription() {
    if (getActivity() == null) return null;
    return getActivity().getCompositeSubscription();
  }
}
