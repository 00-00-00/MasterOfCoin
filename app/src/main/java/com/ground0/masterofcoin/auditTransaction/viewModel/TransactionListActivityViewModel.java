package com.ground0.masterofcoin.auditTransaction.viewModel;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import com.ground0.masterofcoin.auditTransaction.activity.TransactionListActivity;
import com.ground0.masterofcoin.auditTransaction.adapter.TransactionListAdapter;
import com.ground0.masterofcoin.auditTransaction.service.DataPollService;
import com.ground0.masterofcoin.core.baseComponents.BaseActivity;
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
  PendingIntent alarmIntent;

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

  public void initSubscriptions() {
    getCompositeSubscription().add(
        getAppBehaviourBus().filter(event -> event instanceof ExpenseUpdated)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getSubscriptionBuilder().builder().onNext(event -> {
              Log.d(getClass().getSimpleName(), "Updating polled data");
              fetchData();
            }).build()));
  }

  public void initPollService() {
    AlarmManager alarm = (AlarmManager) getActivity().getSystemService(Application.ALARM_SERVICE);
    alarmIntent =
        PendingIntent.getService(getActivity(), 0, new Intent(getActivity(), DataPollService.class),
            0);
    alarm.cancel(alarmIntent);
    alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, 5000,
        alarmIntent);
  }

  public void finishPollService() {
    AlarmManager alarmManager =
        (AlarmManager) getActivity().getSystemService(BaseActivity.ALARM_SERVICE);
    alarmManager.cancel(alarmIntent);
  }
}
