package com.ground0.masterofcoin.auditTransaction.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import butterknife.ButterKnife;
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

  @Override public void registerActivityWithViewModel() {
    viewModel.registerActivity(this);
    viewModel.initSubscriptions();
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    transactionDetailBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_transaction_detail);
    ButterKnife.bind(this);
    transactionDetailBinding.setViewModel(viewModel);
  }

  public void invalidateBinding() {
    transactionDetailBinding.invalidateAll();
  }
}
