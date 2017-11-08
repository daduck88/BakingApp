package com.bakingapp.android.detailrecipe.steps;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bakingapp.android.R;
import com.bakingapp.android.data.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListStepClickListener}
 * interface.
 */
public class StepsFragment extends Fragment {

  private static final String STEPS = "STEPS";
  private List<Step> mSteps = new ArrayList<>();
  private OnListStepClickListener mListener;
  private StepsRecyclerViewAdapter mAdapter;

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
      mAdapter = new StepsRecyclerViewAdapter(mSteps, mListener);
      recyclerView.setAdapter(mAdapter);
    }
    return view;
  }


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if(context instanceof OnListStepClickListener) {
      mListener = (OnListStepClickListener) context;
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
    mAdapter.setSelectedStep(mSteps.get(selectedPosition));
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p/>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnListStepClickListener {
    // TODO: Update argument type and name
    void onListFragmentInteraction(int selectedStepPosition);
  }
}
