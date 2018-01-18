package com.bakingapp.android.detailrecipe.ingredients;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bakingapp.android.App;
import com.bakingapp.android.R;
import com.bakingapp.android.data.Ingredient;
import com.bakingapp.android.data.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class IngredientsFragment extends Fragment {

  private static final String INGREDIENTS = "INGREDIENTS";
  private List<Ingredient> mIngredients = new ArrayList<>();
  private IngredientsAdapter mAdapter;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public IngredientsFragment() {
  }

  // TODO: Customize parameter initialization
  @SuppressWarnings("unused")
  public static IngredientsFragment newInstance(ArrayList<Ingredient> ingredients) {
    IngredientsFragment fragment = new IngredientsFragment();
    Bundle args = new Bundle();
    args.putParcelableArrayList(INGREDIENTS, ingredients);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if(getArguments() != null) {
      mIngredients = getArguments().getParcelableArrayList(INGREDIENTS);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_ingredients_list, container, false);
    // Set the adapter
    Context context = view.getContext();
    RecyclerView recyclerView = (RecyclerView) view;
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    mAdapter = new IngredientsAdapter(mIngredients);
    recyclerView.setAdapter(mAdapter);
    return view;
  }
}
