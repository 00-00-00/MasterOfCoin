package com.ground0.masterofcoin.auditTransaction.service;

import android.content.Intent;
import android.util.Log;
import com.ground0.masterofcoin.core.baseComponents.BaseService;
import com.ground0.masterofcoin.core.event.ExpenseUpdated;
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
    Log.d(getClass().getSimpleName(), "Service Started");
    downloadData();
  }

  public void downloadData() {
    getAppBehaviourSubject().onNext(new ExpenseUpdated(null));
  }
}
