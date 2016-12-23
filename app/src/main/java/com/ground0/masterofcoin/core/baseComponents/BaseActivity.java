package com.ground0.masterofcoin.core.baseComponents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.ground0.masterofcoin.core.event.Event;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zer0 on 22/12/16.
 */

public abstract class BaseActivity extends AppCompatActivity {

  BehaviorSubject<Event> behaviorSubject = BehaviorSubject.create();
  //Need this to un-subscribe all at the end of lifecycle
  CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    registerActivityWithViewModel();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    compositeSubscription.unsubscribe();
  }

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

  public CompositeSubscription getCompositeSubscription() {
    return compositeSubscription;
  }

  public void showToast(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}
