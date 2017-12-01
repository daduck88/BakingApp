package com.bakingapp.android.recipes;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bakingapp.android.BR;
import com.bakingapp.android.R;
import com.bakingapp.android.data.Recipe;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Recipe} and makes a call to the
 * specified {@link RecipeClickListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private List<Recipe> mValues;
    private final RecipeClickListener mListener;

    public RecipesAdapter(List<Recipe> items, RecipeClickListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_recipie, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.onBind(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void updateData(List<Recipe> recipes) {
        mValues = recipes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setVariable(BR.listener, mListener);
        }

        public void onBind(Recipe recipe) {
            binding.setVariable(BR.recipe, recipe);
            binding.executePendingBindings();
        }

    }

    public interface RecipeClickListener {
        void onRecipeClick(Recipe recipe, View view);
    }
}
