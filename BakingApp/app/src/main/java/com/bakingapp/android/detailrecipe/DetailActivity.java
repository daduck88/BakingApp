package com.bakingapp.android.detailrecipe;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.bakingapp.android.R;
import com.bakingapp.android.base.BaseActivity;
import com.bakingapp.android.data.Recipe;
import com.bakingapp.android.data.Step;
import com.bakingapp.android.detailrecipe.step.StepFragment;
import com.bakingapp.android.detailrecipe.steps.StepsFragment;

import java.util.ArrayList;

public class DetailActivity extends BaseActivity implements StepsFragment.OnListStepClickListener, StepFragment.OnPreviousNextStepListener {

  public static final String RECIPE = "RECIPE";
  private Recipe mRecipe;
  private StepsFragment mStepsFragment;
  private int mCurrentStepPosition;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    initData();
    initToolbar();
    initRecipeDetail();
  }

  private void initData() {
    mRecipe = getIntent().getExtras().getParcelable(RECIPE);
  }

  private void initRecipeDetail() {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    mStepsFragment = StepsFragment.newInstance((ArrayList<Step>) mRecipe.getSteps());
    ft.replace(R.id.main_container, mStepsFragment);
    if(isTablet && mRecipe.getSteps() != null && !mRecipe.getSteps().isEmpty()) {
      navigateToStep(mCurrentStepPosition);
      mStepsFragment.setSelectedStep(mCurrentStepPosition);
    }
    ft.commitAllowingStateLoss();
  }

  @Override
  public void onListFragmentInteraction(int selectedStepPosition) {
    if(isTablet) {
      mStepsFragment.setSelectedStep(selectedStepPosition);
    }
    navigateToStep(selectedStepPosition);
  }

  private void navigateToStep(int selectedStepPosition) {
    Step currentStep = mRecipe.getSteps().get(selectedStepPosition);
    mCurrentStepPosition = selectedStepPosition;
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    boolean isFirstStep = selectedStepPosition == 0;
    boolean isLastStep = selectedStepPosition == (mRecipe.getSteps().size() - 1);
    StepFragment stepFragment = StepFragment.newInstance(currentStep, isFirstStep, isLastStep);
    if(isTablet){
      ft.replace(R.id.main_container_detail, stepFragment);
    } else {
      ft.replace(R.id.main_container, stepFragment);
      ft.addToBackStack("step");
    }
    ft.commitAllowingStateLoss();
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
