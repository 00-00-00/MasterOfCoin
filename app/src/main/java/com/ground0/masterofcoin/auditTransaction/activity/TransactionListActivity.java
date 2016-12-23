package com.ground0.masterofcoin.auditTransaction.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ground0.masterofcoin.R;
import com.ground0.masterofcoin.core.baseComponents.BaseActivity;
import com.ground0.masterofcoin.auditTransaction.viewModel.TransactionListActivityViewModel;

/**
 * Created by zer0 on 22/12/16.
 */

public class TransactionListActivity extends BaseActivity {

  TransactionListActivityViewModel viewModel = new TransactionListActivityViewModel();
  @BindView(R.id.a_transaction_list_recycler) RecyclerView recyclerView;

  @Override public void registerActivityWithViewModel() {
    viewModel.registerActivity(this);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_transaction_list);
    ButterKnife.bind(this);
    initRecyclerView();
    viewModel.initSubscriptions();
  }

  @Override protected void onStart() {
    super.onStart();
    viewModel.fetchData();
  }

  private void initRecyclerView() {
    if (recyclerView != null) {
      recyclerView.setAdapter(viewModel.getRecyclerAdapter());
      recyclerView.setLayoutManager(
          new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    viewModel.finishSubscriptions();
  }
}
