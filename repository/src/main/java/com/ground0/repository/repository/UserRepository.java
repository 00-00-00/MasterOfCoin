package com.ground0.repository.repository;

import com.ground0.model.TransactionObject;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by zer0 on 22/12/16.
 */

public interface UserRepository  {

  Observable<TransactionObject> getTransactions();

  Observable<ResponseBody> updateTransactions(TransactionObject transactionObject);
}
