package com.bakingapp.android.detailrecipe.ingredients;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bakingapp.android.BR;
import com.bakingapp.android.R;
import com.bakingapp.android.data.Ingredient;
import com.bakingapp.android.data.Step;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

  private final List<Ingredient> mValues;

  public IngredientsAdapter(List<Ingredient> items) {
    mValues = items;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_ingredient, parent, false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.bind(mValues.get(position));
  }

  @Override
  public int getItemCount() {
    return mValues.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;

    public ViewHolder(ViewDataBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(Ingredient ingredient) {
      binding.setVariable(BR.ingredient, ingredient);
      binding.executePendingBindings();
    }
  }
}
