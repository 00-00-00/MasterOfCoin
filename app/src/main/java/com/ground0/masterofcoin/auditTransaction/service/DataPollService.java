package com.ground0.masterofcoin.auditTransaction.service;

import android.content.Intent;
import android.util.Log;
import com.ground0.masterofcoin.core.baseComponents.BaseService;
import com.ground0.masterofcoin.core.event.ExpenseUpdated;
import com.ground0.model.TransactionObject;
import com.ground0.repository.repository.UserRepository;
import com.ground0.repository.repository.UserRepositoryImpl;

/**
 * Created by zer0 on 23/12/16.
 */

public class DataPollService extends BaseService {

  UserRepository userRepository = UserRepositoryImpl.getInstance();

  public DataPollService() {
    super("DataPollService");
  }

  @Override protected void onHandleIntent(Intent intent) {
    Log.d(getClass().getSimpleName(), "Fetching Data");
    downloadData();
  }

  public void downloadData() {
    getCompositeSubscription().add(userRepository.getTransactions()
        .subscribe(getSubscriptionBuilder().builder().onNext(value -> {
          TransactionObject transactionObject = (TransactionObject) value;
          getAppBehaviourSubject().onNext(new ExpenseUpdated(transactionObject));
        }).onError(error -> error.printStackTrace()).setUnsubscribeOnComplete(true).build()));
  }
}
