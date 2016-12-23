package com.ground0.masterofcoin.auditTransaction.viewModel;

import com.ground0.masterofcoin.auditTransaction.activity.TransactionDetailActivity;
import com.ground0.masterofcoin.core.baseViewModel.BaseActivityViewModel;
import com.ground0.masterofcoin.core.event.ViewDetail;
import com.ground0.model.Expense;
import com.ground0.model.TransactionObject;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by zer0 on 23/12/16.
 */

public class TransactionDetailActivityViewModel
    extends BaseActivityViewModel<TransactionDetailActivity> {

  TransactionObject transactionObject;
  Expense expense;

  public void initSubscriptions() {
    getCompositeSubscription().add(getAppBehaviourBus().filter(event -> event instanceof ViewDetail)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(getSubscriptionBuilder().builder().onNext(event -> {
          ViewDetail.TransactionDetailWrapper data = ((ViewDetail) event).getData();
          transactionObject = data.getTransactionObject();
          expense = data.getExpense();
          getActivity().invalidateBinding();
        }).build()));
  }

  public TransactionObject getTransactionObject() {
    return transactionObject;
  }

  public Expense getExpense() {
    return expense;
  }

  public String getAmount() {
    if (expense == null) return "";
    return expense.getAmount().toPlainString();
  }
}
