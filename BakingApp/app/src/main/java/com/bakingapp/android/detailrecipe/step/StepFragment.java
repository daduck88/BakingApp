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
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

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

  // TODO: Rename and change types of parameters
  private Step mStep;

  private OnPreviousNextStepListener mListener;

  private SimpleExoPlayer mExoPlayer;
  private SimpleExoPlayerView mPlayerView;

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
    step.setNotFirst(!isFirstStep);
    step.setNotLast(!isLastStep);
    args.putParcelable(STEP, step);
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
    mPlayerView = binding.playerView;
    initPlayer();
    return view;
  }

  private void initPlayer() {
    if(mStep.showVideo()){
      if (mExoPlayer == null) {
        // Create an instance of the ExoPlayer.
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        mPlayerView.setPlayer(mExoPlayer);
        // Prepare the MediaSource.
        String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(mStep.getVideoURL()), new DefaultDataSourceFactory(
            getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);
      }
    }
  }

  private void releasePlayer() {
    if(mExoPlayer != null) {
      mExoPlayer.stop();
      mExoPlayer.release();
      mExoPlayer = null;
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    releasePlayer();
  }

  public void setListener(OnPreviousNextStepListener mListener) {
    this.mListener = mListener;
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
