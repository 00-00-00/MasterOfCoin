package com.ground0.masterofcoin.auditTransaction.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.ground0.masterofcoin.R;
import com.ground0.masterofcoin.core.baseComponents.BaseActivity;
import com.ground0.masterofcoin.auditTransaction.viewModel.TransactionListActivityViewModel;

/**
 * Created by zer0 on 22/12/16.
 */

public class TransactionListActivity extends BaseActivity {

  TransactionListActivityViewModel viewModel = new TransactionListActivityViewModel();

  @Override public void registerActivityWithViewModel() {
    viewModel.registerActivity(this);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override protected void onStart() {
    super.onStart();
    viewModel.fetchData();
  }
}
