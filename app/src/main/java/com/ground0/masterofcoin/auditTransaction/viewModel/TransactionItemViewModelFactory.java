package com.ground0.masterofcoin.auditTransaction.viewModel;

import android.graphics.drawable.Drawable;
import android.support.v4.util.Pair;
import android.view.View;
import butterknife.ButterKnife;
import com.ground0.masterofcoin.R;
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
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

        View cardView = view;
        View imageView = ButterKnife.findById(view, R.id.i_transaction_image);

        cardView.setTransitionName("DETAIL_CARD_TRANSITION");
        imageView.setTransitionName("DETAIL_IMAGE_TRANSITION");

        Pair<View, String> pair1 = Pair.create(cardView, cardView.getTransitionName());
        Pair<View, String> pair2 = Pair.create(imageView, imageView.getTransitionName());
        handler.openDetail(expense, pair1, pair2);
      } else {
        handler.openDetail(expense);
      }
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
