package com.ground0.repository.repository;

import com.ground0.model.Object;
import rx.Observable;

/**
 * Created by zer0 on 22/12/16.
 */

public interface UserRepository  {

  Observable<Object> getTransactions();
}
