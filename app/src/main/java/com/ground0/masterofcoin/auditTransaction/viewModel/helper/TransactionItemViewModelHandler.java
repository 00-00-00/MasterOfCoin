package com.ground0.masterofcoin.auditTransaction.viewModel.helper;

import android.graphics.drawable.Drawable;
import android.support.v4.util.Pair;
import android.view.View;
import com.ground0.model.Expense;

/**
 * Created by zer0 on 23/12/16.
 */

public interface TransactionItemViewModelHandler {
  void openDetail(Expense expense, Pair<View, String>... sharedElements);
  Drawable getDrawable(int drawable);
  int getColor(int color);
}
