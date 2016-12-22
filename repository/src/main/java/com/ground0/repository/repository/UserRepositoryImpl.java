package com.ground0.repository.repository;

import com.ground0.model.Object;
import com.ground0.repository.store.ServerStore;
import rx.Observable;

/**
 * Created by zer0 on 22/12/16.
 */

public class UserRepositoryImpl implements UserRepository {

  private static UserRepositoryImpl instance;
  ServerStore serverStore;

  private UserRepositoryImpl() {
    serverStore = ServerStore.getInstance();
  }

  public static UserRepositoryImpl getInstance() {
    if (instance == null) instance = new UserRepositoryImpl();
    return instance;
  }

  @Override public Observable<Object> getTransactions() {
    return serverStore.getTransactions();
  }
}
