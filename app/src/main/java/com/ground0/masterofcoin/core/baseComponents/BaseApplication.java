package com.ground0.masterofcoin.core.baseComponents;

import android.app.Application;
import com.ground0.masterofcoin.core.event.Event;
import rx.subjects.BehaviorSubject;

/**
 * Created by zer0 on 22/12/16.
 */

public class BaseApplication extends Application {

  BehaviorSubject<Event> appBehaviourSubject = BehaviorSubject.create();

  public BehaviorSubject<Event> getAppBehaviourSubject() {
    return appBehaviourSubject;
  }
}