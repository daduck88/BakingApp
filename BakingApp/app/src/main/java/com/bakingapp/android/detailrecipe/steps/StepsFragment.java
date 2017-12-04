package com.bakingapp.android.detailrecipe.steps;

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
import com.bakingapp.android.data.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link StepClickListener}
 * interface.
 */
public class StepsFragment extends Fragment {

  private static final String STEPS = "STEPS";
  private List<Step> mSteps = new ArrayList<>();
  private StepsAdapter.StepClickListener mListener;
  private StepsAdapter mAdapter;
  private int mSelectedStep;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public StepsFragment() {
  }

  // TODO: Customize parameter initialization
  @SuppressWarnings("unused")
  public static StepsFragment newInstance(ArrayList<Step> steps) {
    StepsFragment fragment = new StepsFragment();
    Bundle args = new Bundle();
    args.putParcelableArrayList(STEPS, steps);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if(getArguments() != null) {
      mSteps = getArguments().getParcelableArrayList(STEPS);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_steps_list, container, false);

    // Set the adapter
    if(view instanceof RecyclerView) {
      Context context = view.getContext();
      RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
      mAdapter = new StepsAdapter(mSteps, mListener);
      recyclerView.setAdapter(mAdapter);
      setSelectedStep(mSelectedStep);
    }
    return view;
  }


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if(context instanceof StepsAdapter.StepClickListener) {
      mListener = (StepsAdapter.StepClickListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnListStepClickListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  public void setSelectedStep(int selectedPosition) {
    if(App.isTablet()) {
      mSelectedStep = selectedPosition;
      if (!mSteps.isEmpty()) {
        mAdapter.setSelectedStep(mSteps.get(mSelectedStep));
      }
    }
  }
}
