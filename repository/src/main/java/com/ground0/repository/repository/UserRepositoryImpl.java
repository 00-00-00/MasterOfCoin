package com.ground0.repository.repository;

import com.ground0.model.TransactionObject;
import com.ground0.repository.store.ServerStore;
import okhttp3.ResponseBody;
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

  @Override public Observable<TransactionObject> getTransactions() {
    return serverStore.getTransactions();
  }

  @Override
  public Observable<ResponseBody> updateTransactions(TransactionObject transactionObject) {
    return serverStore.updateTransactions(transactionObject);
  }
}
