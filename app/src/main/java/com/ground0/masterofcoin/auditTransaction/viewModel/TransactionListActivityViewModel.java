package com.ground0.masterofcoin.auditTransaction.viewModel;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.ground0.masterofcoin.auditTransaction.activity.TransactionDetailActivity;
import com.ground0.masterofcoin.auditTransaction.activity.TransactionListActivity;
import com.ground0.masterofcoin.auditTransaction.adapter.TransactionListAdapter;
import com.ground0.masterofcoin.auditTransaction.service.DataPollService;
import com.ground0.masterofcoin.auditTransaction.viewModel.helper.TransactionItemViewModelHandler;
import com.ground0.masterofcoin.core.baseViewModel.BaseActivityViewModel;
import com.ground0.masterofcoin.core.event.ExpenseUpdated;
import com.ground0.masterofcoin.core.event.ViewDetail;
import com.ground0.model.Expense;
import com.ground0.model.TransactionObject;
import com.ground0.repository.repository.UserRepositoryImpl;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by zer0 on 22/12/16.
 */

public class TransactionListActivityViewModel extends BaseActivityViewModel<TransactionListActivity>
    implements TransactionItemViewModelHandler {

  List<Expense> expenses = new ArrayList<>();
  TransactionObject transactionObject;
  TransactionListAdapter transactionListAdapter;
  UserRepositoryImpl userRepository = UserRepositoryImpl.getInstance();
  GcmNetworkManager gcmNetworkManager;
  PendingIntent alarmIntent;

  @Override public void afterRegister() {
    super.afterRegister();
    initSubscriptions();
    gcmNetworkManager = GcmNetworkManager.getInstance(getActivity());
  }

  @Override public Drawable getDrawable(int drawable) {
    return ContextCompat.getDrawable(getActivity(), drawable);
  }

  @Override public int getColor(int color) {
    return ContextCompat.getColor(getActivity(), color);
  }

  public TransactionListAdapter getRecyclerAdapter() {
    if (transactionListAdapter == null) {
      transactionListAdapter = new TransactionListAdapter(this, expenses);
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
    getActivity().onStartDataLoad();
    getCompositeSubscription().add(userRepository.getTransactions()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(getSubscriptionBuilder().builder().onNext(value -> {
          transactionObject = (TransactionObject) value;
          updateData(transactionObject.getExpenses());
          getActivity().onFinishDataLoad();
        }).onError(e -> {
          String errorMessage = "Something went wrong";
          if (e instanceof Throwable) {
            errorMessage = StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : errorMessage;
          }
          getActivity().showError(errorMessage);
        }).setUnsubscribeOnComplete(true).build()));
  }

  public void initSubscriptions() {
    getCompositeSubscription().add(
        getAppPublishBus().filter(event -> event instanceof ExpenseUpdated)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getSubscriptionBuilder().builder().onNext(event -> {
              Log.d(getClass().getSimpleName(), "Updating polled data");
              fetchData();
            }).build()));
  }

  public void initPollService() {
    PeriodicTask task = new PeriodicTask.Builder().setService(DataPollService.class)
        .setTag(DataPollService.class.getSimpleName())
        .setPeriod(30L)
        .build();
    gcmNetworkManager.schedule(task);
  }

  public void finishPollService() {
    gcmNetworkManager.cancelAllTasks(DataPollService.class);
  }

  @Override public void openDetail(Expense expense) {
    getAppBehaviourBus().onNext(new ViewDetail(expense));
    getActivity().startActivity(new Intent(getActivity(), TransactionDetailActivity.class));
  }

  public List<Expense> getData() {
    return expenses;
  }
}
