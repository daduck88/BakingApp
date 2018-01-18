package com.bakingapp.android.detailrecipe.ingredients;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.bakingapp.android.R;
import com.bakingapp.android.base.BaseActivity;
import com.bakingapp.android.data.Ingredient;
import com.bakingapp.android.data.Recipe;
import com.bakingapp.android.data.Step;
import com.bakingapp.android.detailrecipe.step.StepFragment;
import com.bakingapp.android.detailrecipe.steps.StepsAdapter;

import java.util.ArrayList;

public class IngredientsActivity extends BaseActivity {

  public static final String RECIPE = "RECIPE";
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
    initIngredients();
  }

  @Override
  protected void initToolbar() {
    super.initToolbar();
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void initData(Bundle savedInstanceState) {
    mRecipe = getIntent().getExtras().getParcelable(RECIPE);
//    if(savedInstanceState == null) {
//      mCurrentStepPosition = getIntent().getExtras().getInt(STEP_NUMBER);
//    } else {
//      mCurrentStepPosition = savedInstanceState.getInt(STEP_NUMBER);
//    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
//    outState.putInt(STEP_NUMBER, mCurrentStepPosition);
  }

  private void initIngredients() {

    FragmentManager fm = getSupportFragmentManager();
    IngredientsFragment ingredientsFragment = null;
    if(restore) {
      ingredientsFragment = (IngredientsFragment) fm.findFragmentByTag("ingredients");
      restore = false;
    }
    if(ingredientsFragment == null) {
      FragmentTransaction ft = fm.beginTransaction();
      ingredientsFragment = IngredientsFragment.newInstance((ArrayList<Ingredient>) mRecipe.getIngredients());
      ft.replace(R.id.main_container, ingredientsFragment, "ingredients");
      ft.commitAllowingStateLoss();
    }
  }

}
