package com.ground0.masterofcoin.auditTransaction.viewModel;

import com.ground0.model.Expense;

/**
 * Created by zer0 on 23/12/16.
 */

public class TransactionItemViewModelFactory {

  public TransactionItemViewModel createViewModel(Expense expense) {
    return new TransactionItemViewModel(expense);
  }

  public class TransactionItemViewModel {

    Expense expense;

    private TransactionItemViewModel(Expense expense) {
      this.expense = expense;
    }

    public Expense getExpense() {
      return expense;
    }
  }
}
