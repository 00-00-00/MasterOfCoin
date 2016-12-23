package com.ground0.masterofcoin.core.event;

import com.ground0.model.TransactionObject;

/**
 * Created by zer0 on 23/12/16.
 */

public class ExpenseUpdated implements Event<TransactionObject> {

  TransactionObject transactionObject;

  public ExpenseUpdated(TransactionObject transactionObject) {
    this.transactionObject = transactionObject;
  }

  @Override public TransactionObject getData() {
    return transactionObject;
  }
}
