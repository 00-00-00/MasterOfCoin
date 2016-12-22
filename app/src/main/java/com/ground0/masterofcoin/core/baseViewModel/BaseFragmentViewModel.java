package com.ground0.masterofcoin.core.baseViewModel;

import com.ground0.masterofcoin.core.baseComponents.BaseFragment;
import java.lang.ref.WeakReference;

/**
 * Created by zer0 on 22/12/16.
 */

public abstract class BaseFragmentViewModel<T extends BaseFragment> implements ViewModel{

  WeakReference<T> fragment;

  public void register(T fragment) {
    this.fragment = new WeakReference<T>(fragment);
  }


}
