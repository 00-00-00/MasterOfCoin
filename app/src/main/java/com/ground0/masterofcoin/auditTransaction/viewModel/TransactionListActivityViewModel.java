package com.ground0.masterofcoin.auditTransaction.viewModel;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;
import com.ground0.masterofcoin.auditTransaction.activity.TransactionListActivity;
import com.ground0.masterofcoin.auditTransaction.adapter.TransactionListAdapter;
import com.ground0.masterofcoin.auditTransaction.viewModel.helper.TransactionItemViewModelHandler;
import com.ground0.masterofcoin.core.baseViewModel.BaseActivityViewModel;
import com.ground0.masterofcoin.core.event.ExpenseUpdated;
import com.ground0.masterofcoin.core.event.ViewDetail;
import com.ground0.model.Expense;
import com.ground0.model.TransactionObject;
import com.ground0.repository.repository.UserRepositoryImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.commons.lang3.StringUtils;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by zer0 on 22/12/16.
 */

public class TransactionListActivityViewModel extends BaseActivityViewModel<TransactionListActivity>
    implements TransactionItemViewModelHandler {

  private List<Expense> expenses = new ArrayList<>();
  private TransactionObject transactionObject;
  private TransactionListAdapter transactionListAdapter;
  private UserRepositoryImpl userRepository = UserRepositoryImpl.getInstance();
  //private GcmNetworkManager gcmNetworkManager;  //Removing gcm manager for <10s refresh requirement
  private Timer timer = new Timer();

  @Override public void afterRegister() {
    super.afterRegister();
    initSubscriptions();
    //gcmNetworkManager = GcmNetworkManager.getInstance(getActivity());
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
   /* PeriodicTask task = new PeriodicTask.Builder().setService(DataPollService.class)
        .setTag(DataPollService.class.getSimpleName())
        .setPeriod(30L)
        .build();
    gcmNetworkManager.schedule(task);*/

    timer.schedule(new TimerTask() {
      @Override public void run() {
        Log.d(getClass().getSimpleName(), "Timer executing");
        fetchData();
      }
    }, 5 * 1000, 10 * 1000);
  }

  public void finishPollService() {
    timer.cancel();
    //gcmNetworkManager.cancelAllTasks(DataPollService.class);
  }

  @Override public void openDetail(Expense expense, Pair<View, String>... sharedElements) {
    getAppBehaviourBus().onNext(new ViewDetail(expense));
    getActivity().launchTransactionDetailActivity(sharedElements);
  }

  public List<Expense> getData() {
    return expenses;
  }
}
