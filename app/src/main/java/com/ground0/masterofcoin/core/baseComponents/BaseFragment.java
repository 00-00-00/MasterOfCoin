package com.ground0.masterofcoin.core.baseComponents;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by zer0 on 22/12/16.
 */

public abstract class BaseFragment<T extends BaseActivity> extends Fragment {

  public abstract void registerFragmentWithViewModel();

  @Nullable public T getActualActivity() {
    return (T) getActivity();
  }

  @Nullable public BaseApplication getApplication() {
    if (getActualActivity() != null)
      return getActualActivity().getBaseApplication();
    else return null;
  }
}
