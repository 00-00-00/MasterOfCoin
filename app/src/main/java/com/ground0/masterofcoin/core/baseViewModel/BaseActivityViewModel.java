package com.ground0.masterofcoin.core.baseViewModel;

import com.ground0.masterofcoin.core.baseComponents.BaseActivity;
import java.lang.ref.WeakReference;

/**
 * Created by zer0 on 22/12/16.
 */

public abstract class BaseActivityViewModel<T extends BaseActivity> implements ViewModel {

  WeakReference<T> activity;

  public void registerActivity(T activity) {
    this.activity = new WeakReference<T>(activity);
  }

  public T getActivity() {
    return activity.get();
  }
}
