package com.ground0.masterofcoin.core.baseComponents;

import android.support.v7.app.AppCompatActivity;
import com.ground0.masterofcoin.core.event.Event;
import rx.subjects.BehaviorSubject;

/**
 * Created by zer0 on 22/12/16.
 */

public abstract class BaseActivity extends AppCompatActivity {

  BehaviorSubject<Event> behaviorSubject = BehaviorSubject.create();

  public abstract void registerActivityWithViewModel();

  public BaseApplication getBaseApplication() {
    return (BaseApplication) getApplication();
  }

  public BehaviorSubject<Event> getAppBehaviourSubject() {
    return getBaseApplication().getAppBehaviourSubject();
  }

  public BehaviorSubject<Event> getActivityBehaviourSubject() {
    return behaviorSubject;
  }
}
