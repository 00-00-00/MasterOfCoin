package com.ground0.masterofcoin.core.baseComponents;

import android.app.Service;
import com.ground0.masterofcoin.core.event.Event;
import rx.subjects.BehaviorSubject;

/**
 * Created by zer0 on 22/12/16.
 */

public abstract class BaseService extends Service {

  public BaseApplication getBaseApplication() {
    return (BaseApplication) getApplication();
  }

  public BehaviorSubject<Event> getAppBehaviourSubject() {
    return getBaseApplication().getAppBehaviourSubject();
  }
}
