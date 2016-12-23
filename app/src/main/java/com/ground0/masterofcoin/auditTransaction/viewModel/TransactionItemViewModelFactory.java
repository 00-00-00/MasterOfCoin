package com.ground0.masterofcoin.auditTransaction.viewModel;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.ground0.masterofcoin.auditTransaction.viewModel.helper.TransactionItemViewModelHandler;
import com.ground0.masterofcoin.core.util.ResourceUtil;
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
    Drawable drawable;
    int state;

    private TransactionItemViewModel(Expense expense) {
      this.expense = expense;
      this.drawable = handler.getDrawable(ResourceUtil.getDrawable(expense.getCategory()));
      this.state = handler.getColor(ResourceUtil.getColor(expense.getState()));
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

    public Drawable getDrawable() {
      return drawable;
    }

    public int getState() {
      return state;
    }
  }
}
