package com.ground0.masterofcoin.core.event;

import com.ground0.event.Event;

/**
 * Created by zer0 on 23/12/16.
 */

public class ExpenseUpdated implements Event<Void> {

  public ExpenseUpdated() {
  }

  @Override public Void getData() {
    return null;
  }
}
