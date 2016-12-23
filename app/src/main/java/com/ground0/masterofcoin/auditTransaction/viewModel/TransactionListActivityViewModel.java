package com.ground0.masterofcoin.auditTransaction.viewModel;

import com.ground0.masterofcoin.auditTransaction.activity.TransactionListActivity;
import com.ground0.masterofcoin.auditTransaction.adapter.TransactionListAdapter;
import com.ground0.masterofcoin.core.baseViewModel.BaseActivityViewModel;
import com.ground0.masterofcoin.core.event.ExpenseUpdated;
import com.ground0.model.Expense;
import com.ground0.model.TransactionObject;
import com.ground0.repository.repository.UserRepositoryImpl;
import java.util.ArrayList;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by zer0 on 22/12/16.
 */

public class TransactionListActivityViewModel
    extends BaseActivityViewModel<TransactionListActivity> {

  List<Expense> expenses = new ArrayList<>();
  TransactionListAdapter transactionListAdapter;
  UserRepositoryImpl userRepository = UserRepositoryImpl.getInstance();

  public TransactionListAdapter getRecyclerAdapter() {
    if (transactionListAdapter == null) {
      transactionListAdapter = new TransactionListAdapter(expenses);
    }
    return transactionListAdapter;
  }

  public void updateData(List<Expense> expenses) {
    if (expenses == null) return;
    this.expenses.clear();
    this.expenses.addAll(expenses);
    if (transactionListAdapter != null) transactionListAdapter.notifyDataSetChanged();
  }

  public void fetchData() {
    getCompositeSubscription().add(userRepository.getTransactions()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(getSubscriptionBuilder().builder().onNext(value -> {
          TransactionObject transactionObject = (TransactionObject) value;
          updateData(transactionObject.getExpenses());
        }).onError(error -> {
          error.printStackTrace();
        }).setUnsubscribeOnComplete(true).build()));
  }

  public void finishSubscriptions() {

  }

  public void initSubscriptions() {
    getAppBehaviourBus().filter(event -> event instanceof ExpenseUpdated)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(getSubscriptionBuilder().builder().onNext(event -> {
          TransactionObject transactionObject = ((ExpenseUpdated) event).getData();
          if (transactionObject != null) updateData(transactionObject.getExpenses());
        }).build());
  }
}
