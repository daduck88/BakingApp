package com.bakingapp.android.detailrecipe.step;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.bakingapp.android.R;
import com.bakingapp.android.base.BaseActivity;
import com.bakingapp.android.data.Recipe;
import com.bakingapp.android.data.Step;
import com.bakingapp.android.detailrecipe.steps.StepsAdapter;

public class StepActivity extends BaseActivity implements StepsAdapter.StepClickListener, StepFragment.OnPreviousNextStepListener {

  public static final String RECIPE = "RECIPE";
  public static final String STEP_NUMBER = "STEP_NUMBER";
  private Recipe mRecipe;
  private int mCurrentStepPosition;
  private boolean restore;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    overrideOrientation = false;
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    restore = savedInstanceState != null;
    initData(savedInstanceState);
    initToolbar();
    navigateToStep(mCurrentStepPosition);
  }

  @Override
  protected void initToolbar() {
    super.initToolbar();
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void initData(Bundle savedInstanceState) {
    mRecipe = getIntent().getExtras().getParcelable(RECIPE);
    if(savedInstanceState == null) {
      mCurrentStepPosition = getIntent().getExtras().getInt(STEP_NUMBER);
    } else {
      mCurrentStepPosition = savedInstanceState.getInt(STEP_NUMBER);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(STEP_NUMBER, mCurrentStepPosition);
  }

  @Override
  public void onStepClick(int position, View view) {
    navigateToStep(position);
  }

  @Override
  public void onIngredientsClick() {
    //do nothing
  }

  private void navigateToStep(int selectedStepPosition) {
    Step currentStep = mRecipe.getSteps().get(selectedStepPosition);
    mCurrentStepPosition = selectedStepPosition;
    FragmentManager fm = getSupportFragmentManager();
    StepFragment stepFragment = null;
    if(restore) {
      stepFragment = (StepFragment) fm.findFragmentByTag("step");
      restore = false;
    }
    if(stepFragment == null) {
      FragmentTransaction ft = fm.beginTransaction();
      boolean isFirstStep = selectedStepPosition == 0;
      boolean isLastStep = selectedStepPosition == (mRecipe.getSteps().size() - 1);
      stepFragment = StepFragment.newInstance(currentStep, isFirstStep, isLastStep);
      ft.replace(R.id.main_container, stepFragment, "step");
      ft.commitAllowingStateLoss();
    }
    stepFragment.setListener(this);
  }

  @Override
  public void onPreviousStepClick() {
    navigateToStep(--mCurrentStepPosition);
  }

  @Override
  public void onNextStepClick() {
    navigateToStep(++mCurrentStepPosition);
  }
}
