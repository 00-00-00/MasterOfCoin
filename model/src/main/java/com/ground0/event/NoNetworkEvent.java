package com.ground0.event;

/**
 * Created by zer0 on 24/12/16.
 */

public class NoNetworkEvent implements Event<Void> {

  public NoNetworkEvent() {

  }

  @Override public Void getData() {
    return null;
  }
}
