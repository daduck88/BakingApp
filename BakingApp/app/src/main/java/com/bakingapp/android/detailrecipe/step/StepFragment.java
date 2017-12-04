package com.bakingapp.android.detailrecipe.step;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bakingapp.android.R;
import com.bakingapp.android.data.Step;
import com.bakingapp.android.databinding.FragmentStepBinding;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnPreviousNextStepListener} interface
 * to handle interaction events.
 * Use the {@link StepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String STEP = "STEP";
  private static final String IS_FIRST_STEP = "IS_FIRST_STEP";
  private static final String IS_LAST_STEP = "IS_LAST_STEP";

  // TODO: Rename and change types of parameters
  private Step mStep;
  private boolean isFirstStep;
  private boolean isLastStep;

  private OnPreviousNextStepListener mListener;

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param step step to show details.
   * @return A new instance of fragment StepFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static StepFragment newInstance(Step step, boolean isFirstStep,boolean isLastStep) {
    StepFragment fragment = new StepFragment();
    Bundle args = new Bundle();
    args.putParcelable(STEP, step);
    args.putBoolean(IS_FIRST_STEP, isFirstStep);
    args.putBoolean(IS_LAST_STEP, isLastStep);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if(getArguments() != null) {
      mStep = getArguments().getParcelable(STEP);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    FragmentStepBinding binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_step, container, false);
    View view = binding.getRoot();
    //here data must be an instance of the class MarsDataProvider
    binding.setStep(mStep);
    binding.setListener(mListener);
    return view;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if(context instanceof OnPreviousNextStepListener) {
      mListener = (OnPreviousNextStepListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnPreviousNextStepListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnPreviousNextStepListener {
    // TODO: Update argument type and name
    void onPreviousStepClick();
    void onNextStepClick();
  }
}
