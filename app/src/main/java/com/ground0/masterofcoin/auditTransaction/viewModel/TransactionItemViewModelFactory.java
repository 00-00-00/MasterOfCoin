package com.ground0.masterofcoin.auditTransaction.viewModel;

import android.view.View;
import com.ground0.masterofcoin.auditTransaction.viewModel.helper.TransactionItemViewModelHandler;
import com.ground0.model.Expense;

/**
 * Created by zer0 on 23/12/16.
 */

public class TransactionItemViewModelFactory {

  TransactionItemViewModelHandler handler;

  public TransactionItemViewModelFactory(TransactionItemViewModelHandler handler) {
    this.handler = handler;
  }

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

    public void openDetail(View view) {
      handler.openDetail(expense);
    }

    public String getAmount() {
      return expense.getAmount().toPlainString();
    }
  }
}
