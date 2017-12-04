package com.bakingapp.android.detailrecipe.steps;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.android.BR;
import com.bakingapp.android.R;
import com.bakingapp.android.data.Recipe;
import com.bakingapp.android.data.Step;
import com.bakingapp.android.recipes.RecipesAdapter;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

  private final List<Step> mValues;
  private final StepClickListener mListener;
  private Step mSelectedStep;

  public StepsAdapter(List<Step> items, StepClickListener listener) {
    mValues = items;
    mListener = listener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_step, parent, false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    Step step = mValues.get(position);
    step.setPosition(position);
    step.setNotFirst(position != 0);
    step.setNotLast(position != (mValues.size() - 1));
    holder.bind(step);
  }

  @Override
  public int getItemCount() {
    return mValues.size();
  }

  public void setSelectedStep(Step item) {
    mSelectedStep = item;
    notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;

    public ViewHolder(ViewDataBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      this.binding.setVariable(BR.listener, mListener);
    }

    public void bind(Step step) {
      binding.setVariable(BR.step, step);
      binding.executePendingBindings();
      itemView.setSelected(step == mSelectedStep);//TODO add selected state in R.layout.item_step
    }
  }

  public interface StepClickListener {
    void onStepClick(int position, View view);
  }
}
