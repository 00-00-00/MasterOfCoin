package com.ground0.masterofcoin.auditTransaction.service;

import android.util.Log;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.TaskParams;
import com.ground0.masterofcoin.core.baseComponents.BaseService;
import com.ground0.masterofcoin.core.event.ExpenseUpdated;

/**
 * Created by zer0 on 23/12/16.
 */

public class DataPollService extends BaseService {

  @Override public int onRunTask(TaskParams taskParams) {
    Log.d(getClass().getSimpleName(), "Service Started");
    publishEvent();
    return GcmNetworkManager.RESULT_SUCCESS;
  }

  public void publishEvent() {
    getAppPublishSubject().onNext(new ExpenseUpdated());
  }
}
