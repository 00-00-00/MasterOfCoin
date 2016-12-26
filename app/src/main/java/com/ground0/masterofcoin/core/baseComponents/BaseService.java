package com.ground0.masterofcoin.core.baseComponents;

import android.app.IntentService;
import com.ground0.event.Event;
import com.ground0.masterofcoin.core.rx.SubscriptionBuilder;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zer0 on 22/12/16.
 */

public abstract class BaseService extends IntentService {

  SubscriptionBuilder subscriptionBuilder = SubscriptionBuilder.getInstance();
  CompositeSubscription compositeSubscription = new CompositeSubscription();

  public BaseService(String name) {
    super(name);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    compositeSubscription.unsubscribe();
  }

  public BaseApplication getBaseApplication() {
    return (BaseApplication) getApplication();
  }

  public BehaviorSubject<Event> getAppBehaviourSubject() {
    return getBaseApplication().getAppBehaviourSubject();
  }

  public PublishSubject<Event> getAppPublishSubject() {
    return getBaseApplication().getAppPublishSubject();
  }

  public SubscriptionBuilder getSubscriptionBuilder() {
    return subscriptionBuilder;
  }

  public CompositeSubscription getCompositeSubscription() {
    return compositeSubscription;
  }
}
