package com.ground0.masterofcoin.auditTransaction.activity;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import com.ground0.masterofcoin.R;
import com.ground0.masterofcoin.auditTransaction.viewModel.TransactionDetailActivityViewModel;
import com.ground0.masterofcoin.core.baseComponents.BaseActivity;
import com.ground0.masterofcoin.databinding.ActivityTransactionDetailBinding;

/**
 * Created by zer0 on 23/12/16.
 */

public class TransactionDetailActivity extends BaseActivity {

  TransactionDetailActivityViewModel viewModel = new TransactionDetailActivityViewModel();
  ActivityTransactionDetailBinding transactionDetailBinding;
  @BindView(R.id.a_transaction_detail_image) View imageView;
  @BindView(R.id.a_transaction_detail_card) View cardView;

  @Override public void registerActivityWithViewModel() {
    viewModel.registerActivity(this);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    transactionDetailBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_transaction_detail);
    ButterKnife.bind(this);
    transactionDetailBinding.setViewModel(viewModel);
    initTransitions();
  }

  private void initTransitions() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      cardView.setTransitionName("DETAIL_CARD_TRANSITION");
      imageView.setTransitionName("DETAIL_IMAGE_TRANSITION");
    }
  }

  public void invalidateBinding() {
    transactionDetailBinding.invalidateAll();
  }

  @OnCheckedChanged(R.id.a_transaction_detail_fraudlent)
  public void onFraudlentCheckboxChecked(boolean checked) {
    viewModel.setTransactionAsFraudlent(checked);
  }

  @OnCheckedChanged(R.id.a_transaction_detail_verified)
  public void onVerifiedCheckboxChecked(boolean checked) {
    viewModel.setTransactionVerification(checked);
  }

  public void showError(String errorMessage) {
    View view = getWindow().getDecorView().getRootView().findViewById(android.R.id.content);
    Snackbar.make(view, errorMessage, Snackbar.LENGTH_INDEFINITE)
        .setAction("Retry", v -> viewModel.retry())
        .show();
  }
}
