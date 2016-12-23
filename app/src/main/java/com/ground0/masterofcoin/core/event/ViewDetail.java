package com.ground0.masterofcoin.core.event;

import com.ground0.model.Expense;

/**
 * Created by zer0 on 23/12/16.
 */

public class ViewDetail implements Event<Expense> {

  Expense expense;

  public ViewDetail(Expense expense) {
    this.expense = expense;
  }

  @Override public Expense getData() {
    return expense;
  }

  /*public class TransactionDetailWrapper {
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
  }*/
}
