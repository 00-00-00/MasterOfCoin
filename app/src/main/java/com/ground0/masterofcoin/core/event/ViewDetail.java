package com.ground0.masterofcoin.core.event;

import com.ground0.model.Expense;
import com.ground0.model.TransactionObject;

/**
 * Created by zer0 on 23/12/16.
 */

public class ViewDetail implements Event<ViewDetail.TransactionDetailWrapper> {

  TransactionDetailWrapper transactionDetailWrapper;

  public ViewDetail(Expense expense, TransactionObject transactionObject) {
    this.transactionDetailWrapper = new TransactionDetailWrapper(expense, transactionObject);
  }

  @Override public TransactionDetailWrapper getData() {
    return transactionDetailWrapper;
  }

  public class TransactionDetailWrapper {
    TransactionObject transactionObject;
    Expense expense;

    public TransactionDetailWrapper(Expense expense, TransactionObject transactionObject) {
      this.expense = expense;
      this.transactionObject = transactionObject;
    }

    public TransactionObject getTransactionObject() {
      return transactionObject;
    }

    public Expense getExpense() {
      return expense;
    }
  }
}
