package com.ground0.masterofcoin.auditTransaction.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ground0.masterofcoin.R;
import com.ground0.masterofcoin.auditTransaction.viewModel.TransactionListActivityViewModel;
import com.ground0.masterofcoin.core.baseComponents.BaseActivity;

/**
 * Created by zer0 on 22/12/16.
 */

public class TransactionListActivity extends BaseActivity {

  TransactionListActivityViewModel viewModel = new TransactionListActivityViewModel();
  @BindView(R.id.a_transaction_list_recycler) RecyclerView recyclerView;
  @BindView(R.id.a_transaction_empty_view) View emptyView;
  @BindView(R.id.a_transaction_swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;
  boolean isLoading;

  @Override public void registerActivityWithViewModel() {
    viewModel.registerActivity(this);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_transaction_list);
    ButterKnife.bind(this);
    initRecyclerView();
    initSwipeRefreshLayout();
    invalidateSwipeRefresh();
  }

  @Override protected void onStart() {
    super.onStart();
    viewModel.fetchData();
    viewModel.initPollService();
  }

  private void initRecyclerView() {
    if (recyclerView != null) {
      recyclerView.setAdapter(viewModel.getRecyclerAdapter());
      recyclerView.setLayoutManager(
          new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
  }

  private void initSwipeRefreshLayout() {
    swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.wageningen_green),
        ContextCompat.getColor(this, R.color.persian_red),
        ContextCompat.getColor(this, R.color.yellow_orange),
        ContextCompat.getColor(this, R.color.spanish_green));
    swipeRefreshLayout.setOnRefreshListener(() -> {
      viewModel.fetchData();
    });
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    viewModel.finishPollService();
  }

  public void toggleEmptyView(boolean show) {
    emptyView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
  }

  public void invalidateSwipeRefresh() {
    swipeRefreshLayout.post(() -> {
      swipeRefreshLayout.setRefreshing(isLoading);
    });
  }

  public void showError(String errorMessage) {
    onFinishDataLoad();
    View view = getWindow().getDecorView().getRootView().findViewById(android.R.id.content);
    Snackbar.make(view, errorMessage, Snackbar.LENGTH_INDEFINITE)
        .setAction("Retry", v -> viewModel.fetchData())
        .show();
  }

  public void onStartDataLoad() {
    isLoading = true;
    if (swipeRefreshLayout != null) {
      invalidateSwipeRefresh();
    }
  }

  public void onFinishDataLoad() {
    isLoading = false;
    invalidateSwipeRefresh();
    if (viewModel.getData().size() == 0) {
      toggleEmptyView(true);
    } else {
      toggleEmptyView(false);
    }
  }

  public void launchTransactionDetailActivity(Pair<View, String>... transitionElements) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Intent intent = new Intent(this, TransactionDetailActivity.class);
      ActivityOptionsCompat options = ActivityOptionsCompat.
          makeSceneTransitionAnimation(this, transitionElements);
      startActivity(intent, options.toBundle());
    } else {
      startActivity(new Intent(this, TransactionDetailActivity.class));
    }
  }
}
