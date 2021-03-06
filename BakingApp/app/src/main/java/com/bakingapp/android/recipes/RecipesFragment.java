package com.bakingapp.android.recipes;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bakingapp.android.App;
import com.bakingapp.android.R;
import com.bakingapp.android.data.Recipe;
import com.bakingapp.android.model.service.repository.RecipesDataRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link RecipesAdapter.RecipeClickListener}
 * interface.
 */
public class RecipesFragment extends Fragment {

  // TODO: Customize parameter argument names
  private static final String ARG_COLUMN_COUNT = "column-count";
  // TODO: Customize parameters
  private int mColumnCount = 2;
  private RecipesAdapter.RecipeClickListener mListener;

  @VisibleForTesting
  CountingIdlingResource espressoTestIdlingResource = new CountingIdlingResource("Network_Call");

  @Inject
  RecipesDataRepository recipesDataRepository;
  private RecipesAdapter adapter;
  private LinearLayoutManager lManager;
  private int positionIndex;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public RecipesFragment() {
  }

  // TODO: Customize parameter initialization
  @SuppressWarnings("unused")
  public static RecipesFragment newInstance(int columnCount) {
    RecipesFragment fragment = new RecipesFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_COLUMN_COUNT, columnCount);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    App.getAppComponent(getActivity()).inject(this);
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    if(getArguments() != null) {
      mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_recipie_list, container, false);

    // Set the adapter
    if(view instanceof RecyclerView) {
      Context context = view.getContext();
      RecyclerView rvList = (RecyclerView) view;
      lManager = null;
      if(mColumnCount <= 1) {
        lManager = new LinearLayoutManager(context);
      } else {
        lManager = new GridLayoutManager(context, mColumnCount);
      }
      rvList.setLayoutManager(lManager);
      adapter = new RecipesAdapter(new ArrayList<Recipe>(), mListener);
      rvList.setAdapter(adapter);
    }
    initData();
    return view;
  }

  private void initData() {
    espressoTestIdlingResource.increment();
    recipesDataRepository.getRecipes().subscribe(this::displayStockResults, this::displayErrors);
  }

  private void displayErrors(Throwable throwable) {
    //    String message = throwable.getMessage();
    //    if (throwable instanceof NoSuchElementException) {
    //      message = "Enter a stock symbol first!!";
    //    }
    //    binding.errorMessage.setVisibility(View.VISIBLE);
    //    binding.errorMessage.setText(message);
    //TODO show error message
    throwable.printStackTrace();
  }

  private void displayStockResults(List<Recipe> recipes) {
    adapter.updateData(recipes);
    if(!recipes.isEmpty() && !espressoTestIdlingResource.isIdleNow()) {
      espressoTestIdlingResource.decrement();
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if(context instanceof RecipesAdapter.RecipeClickListener) {
      mListener = (RecipesAdapter.RecipeClickListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnListStepClickListener");
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    positionIndex = lManager.findFirstCompletelyVisibleItemPosition();
  }

  @Override
  public void onResume() {
    super.onResume();
    lManager.scrollToPosition(positionIndex);
    positionIndex = 0;
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @VisibleForTesting
  public CountingIdlingResource getEspressoIdlingResourceForRecipesFragment() {
    return espressoTestIdlingResource;
  }
}
