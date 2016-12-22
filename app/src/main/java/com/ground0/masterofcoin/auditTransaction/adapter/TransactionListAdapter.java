package com.ground0.masterofcoin.auditTransaction.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ground0.masterofcoin.R;
import com.ground0.masterofcoin.auditTransaction.viewModel.TransactionItemViewModelFactory;
import com.ground0.masterofcoin.databinding.ItemTransactionBinding;
import com.ground0.model.Expense;
import java.util.List;

/**
 * Created by zer0 on 23/12/16.
 */

public class TransactionListAdapter
    extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder> {

  List<Expense> data;
  LayoutInflater layoutInflater;
  TransactionItemViewModelFactory viewModelFactory;

  public TransactionListAdapter(@NonNull List<Expense> data) {
    this.data = data;
    viewModelFactory = new TransactionItemViewModelFactory();
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (layoutInflater == null) layoutInflater = LayoutInflater.from(parent.getContext());
    View itemView = layoutInflater.inflate(R.layout.item_transaction, parent, false);
    return new ViewHolder(itemView);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    ItemTransactionBinding itemBinding = DataBindingUtil.bind(holder.itemView);
    itemBinding.setViewModel(viewModelFactory.createViewModel(data.get(position)));
  }

  @Override public int getItemCount() {
    return data.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View itemView) {
      super(itemView);
    }
  }
}
