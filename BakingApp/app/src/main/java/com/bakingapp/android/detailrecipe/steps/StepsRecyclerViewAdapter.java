package com.bakingapp.android.detailrecipe.steps;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.android.R;
import com.bakingapp.android.data.Step;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Step} and makes a call to the
 * specified {@link StepsFragment.OnListStepClickListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class StepsRecyclerViewAdapter extends RecyclerView.Adapter<StepsRecyclerViewAdapter.ViewHolder> {

  private final List<Step> mValues;
  private final StepsFragment.OnListStepClickListener mListener;
  private Step mSelectedStep;

  public StepsRecyclerViewAdapter(List<Step> items, StepsFragment.OnListStepClickListener listener) {
    mValues = items;
    mListener = listener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_step, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.bind(mValues.get(position));
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
    private final TextView mIdView;
    private final TextView mContentView;
    private Step mItem;

    public ViewHolder(View view) {
      super(view);
      mIdView = (TextView) view.findViewById(R.id.id);
      mContentView = (TextView) view.findViewById(R.id.content);
      view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if(null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onListFragmentInteraction(getAdapterPosition());
          }
        }
      });
    }

    @Override
    public String toString() {
      return super.toString() + " '" + mContentView.getText() + "'";
    }

    public void bind(Step step) {
      mItem = step;
      mIdView.setText(mItem.getId());
      mContentView.setText(mItem.getDescription());
      itemView.setSelected(mItem == mSelectedStep);//TODO add selected state in R.layout.item_step
    }
  }
}
