package com.ground0.masterofcoin.auditTransaction.viewModel.helper;

import android.graphics.drawable.Drawable;
import com.ground0.model.Expense;

/**
 * Created by zer0 on 23/12/16.
 */

public interface TransactionItemViewModelHandler {
  void openDetail(Expense expense);
  Drawable getDrawable(int drawable);
  int getColor(int color);
}
