package com.bakingapp.android.detailrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.bakingapp.android.App;
import com.bakingapp.android.R;
import com.bakingapp.android.base.BaseActivity;
import com.bakingapp.android.data.Ingredient;
import com.bakingapp.android.data.Recipe;
import com.bakingapp.android.data.Step;
import com.bakingapp.android.detailrecipe.ingredients.IngredientsActivity;
import com.bakingapp.android.detailrecipe.ingredients.IngredientsFragment;
import com.bakingapp.android.detailrecipe.step.StepActivity;
import com.bakingapp.android.detailrecipe.step.StepFragment;
import com.bakingapp.android.detailrecipe.steps.StepsAdapter;
import com.bakingapp.android.detailrecipe.steps.StepsFragment;

import java.util.ArrayList;

public class DetailActivity extends BaseActivity implements StepsAdapter.StepClickListener {

  public static final String RECIPE = "RECIPE";
  private static final String CURRENT_POSITION = "CURRENT_POSITION";
  private Recipe mRecipe;
  private StepsFragment mStepsFragment;
  private int mCurrentStepPosition;
  private boolean restore;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    initData(savedInstanceState);
    initToolbar();
    initRecipeDetail();
  }

  @Override
  protected void initToolbar() {
    super.initToolbar();
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void initData(Bundle savedInstanceState) {
    mRecipe = getIntent().getExtras().getParcelable(RECIPE);
    if(savedInstanceState != null) {
      restore = true;
      mCurrentStepPosition = savedInstanceState.getInt(CURRENT_POSITION);
    }
  }

  private void initRecipeDetail() {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    mStepsFragment = StepsFragment.newInstance((ArrayList<Step>) mRecipe.getSteps());
    ft.replace(R.id.main_container, mStepsFragment);
    if(App.isTablet() && mRecipe.getSteps() != null && !mRecipe.getSteps().isEmpty()) {
      if(mCurrentStepPosition == -1){
        onIngredientsClick();
      } else {
        navigateToStep(mCurrentStepPosition);
      }
      mStepsFragment.setSelectedStep(mCurrentStepPosition);
    }
    ft.commitAllowingStateLoss();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(RECIPE, mRecipe);
    outState.putInt(CURRENT_POSITION, mCurrentStepPosition);
  }

  @Override
  public void onStepClick(int position, View view) {
    if(App.isTablet()) {
      mStepsFragment.setSelectedStep(position);
    }
    navigateToStep(position);
  }

  @Override
  public void onIngredientsClick() {
    if(App.isTablet()) {
      mCurrentStepPosition = -1;
      FragmentManager fm = getSupportFragmentManager();
      IngredientsFragment ingredientsFragment = null;
      if(restore) {
        ingredientsFragment = (IngredientsFragment) fm.findFragmentByTag("ingredients");
        restore = false;
      }
      if(ingredientsFragment == null) {
        FragmentTransaction ft = fm.beginTransaction();
        ingredientsFragment = IngredientsFragment.newInstance((ArrayList<Ingredient>) mRecipe.getIngredients());
        ft.replace(R.id.main_container_detail, ingredientsFragment, "ingredients");
        ft.commitAllowingStateLoss();
      }
      mStepsFragment.setSelectedStep(mCurrentStepPosition);
    } else {
      Intent intent = new Intent(DetailActivity.this, IngredientsActivity.class);
      intent.putExtra(IngredientsActivity.RECIPE, mRecipe);
      startActivity(intent);
    }
  }

  private void navigateToStep(int selectedStepPosition) {
    if(App.isTablet()) {
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
        stepFragment = StepFragment.newInstance(currentStep, false, false);
        ft.replace(R.id.main_container_detail, stepFragment, "step");
        ft.commitAllowingStateLoss();
      }
    } else {
      Intent intent = new Intent(DetailActivity.this, StepActivity.class);
      intent.putExtra(StepActivity.RECIPE, mRecipe);
      intent.putExtra(StepActivity.STEP_NUMBER, selectedStepPosition);
      startActivity(intent);
    }
  }
}
