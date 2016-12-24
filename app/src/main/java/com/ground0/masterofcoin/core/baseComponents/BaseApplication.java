package com.ground0.masterofcoin.core.baseComponents;

import android.app.Application;
import com.ground0.event.Event;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Created by zer0 on 22/12/16.
 */

public class BaseApplication extends Application {

  BehaviorSubject<Event> appBehaviourSubject = BehaviorSubject.create();
  PublishSubject<Event> appPublishSubject = PublishSubject.create();

  public BehaviorSubject<Event> getAppBehaviourSubject() {
    return appBehaviourSubject;
  }

  public PublishSubject<Event> getAppPublishSubject() {
    return appPublishSubject;
  }

  @Override public void onCreate() {
    super.onCreate();
  }
}
