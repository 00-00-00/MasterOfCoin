package com.ground0.masterofcoin.auditTransaction.viewModel;

import android.databinding.ObservableField;
import android.view.View;
import com.ground0.masterofcoin.auditTransaction.activity.TransactionDetailActivity;
import com.ground0.masterofcoin.core.baseViewModel.BaseActivityViewModel;
import com.ground0.masterofcoin.core.event.ViewDetail;
import com.ground0.model.Expense;
import com.ground0.model.TransactionObject;
import com.ground0.repository.repository.UserRepository;
import com.ground0.repository.repository.UserRepositoryImpl;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by zer0 on 23/12/16.
 */

public class TransactionDetailActivityViewModel
    extends BaseActivityViewModel<TransactionDetailActivity> {

  UserRepository userRepository = UserRepositoryImpl.getInstance();
  TransactionObject transactionObject;
  Expense expense;
  ObservableField<Boolean> isFraud = new ObservableField<>(false);
  ObservableField<Boolean> isVerified = new ObservableField<>(false);

  public void initSubscriptions() {
    getCompositeSubscription().add(getAppBehaviourBus().filter(event -> event instanceof ViewDetail)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(getSubscriptionBuilder().builder().onNext(event -> {
          ViewDetail.TransactionDetailWrapper data = ((ViewDetail) event).getData();
          transactionObject = data.getTransactionObject();
          expense = data.getExpense();
          initFields();
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

  public ObservableField<Boolean> getIsFraud() {
    return isFraud;
  }

  public ObservableField<Boolean> getIsVerified() {
    return isVerified;
  }

  private void initFields() {
    isFraud.set("fraud".equals(expense.getState()));
    isVerified.set("verified".equals(expense.getState()));
  }

  public void setTransactionVerification(boolean transactionVerification) {
    isVerified.set(transactionVerification);
    expense.setState(transactionVerification ? "verified" : "unverified");
  }

  public void setTransactionAsFraudlent(boolean transactionAsFraudlent) {
    isFraud.set(transactionAsFraudlent);
    expense.setState(transactionAsFraudlent ? "fraud" : expense.getCategory());
  }

  public void onSave(View view) {
    List<Expense> expenses = transactionObject.getExpenses();
    for (int i = 0; i < expenses.size(); i++) {
      if (expenses.get(i).equals(this.expense)) {
        expenses.set(i, this.expense);
      }
    }
    transactionObject.setExpenses(expenses);
    saveData();
  }

  private void saveData() {
    getCompositeSubscription().add(userRepository.updateTransactions(transactionObject)
        .subscribe(getSubscriptionBuilder().builder().onNext(value -> {

        }).onError(e -> {

        }).build()));
  }
}
