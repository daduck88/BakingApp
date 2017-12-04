package com.bakingapp.android.detailrecipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.bakingapp.android.App;
import com.bakingapp.android.R;
import com.bakingapp.android.base.BaseActivity;
import com.bakingapp.android.data.Recipe;
import com.bakingapp.android.data.Step;
import com.bakingapp.android.detailrecipe.step.StepFragment;
import com.bakingapp.android.detailrecipe.steps.StepsAdapter;
import com.bakingapp.android.detailrecipe.steps.StepsFragment;

import java.util.ArrayList;

public class DetailActivity extends BaseActivity implements StepsAdapter.StepClickListener, StepFragment.OnPreviousNextStepListener {

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
        if (App.isTablet() && mRecipe.getSteps() != null && !mRecipe.getSteps().isEmpty()) {
            navigateToStep(mCurrentStepPosition);
            mStepsFragment.setSelectedStep(mCurrentStepPosition);
        }
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onStepClick(int position, View view) {
        if (App.isTablet()) {
            mStepsFragment.setSelectedStep(position);
        }
        navigateToStep(position);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("step");
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

    private void navigateToStep(int selectedStepPosition) {
        Step currentStep = mRecipe.getSteps().get(selectedStepPosition);
        mCurrentStepPosition = selectedStepPosition;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        boolean isFirstStep = selectedStepPosition == 0;
        boolean isLastStep = selectedStepPosition == (mRecipe.getSteps().size() - 1);
        StepFragment stepFragment = StepFragment.newInstance(currentStep, isFirstStep, isLastStep);
        if (App.isTablet()) {
            ft.replace(R.id.main_container_detail, stepFragment);
            mStepsFragment.setSelectedStep(selectedStepPosition);
        } else {
            ft.replace(R.id.main_container, stepFragment, "step");
            if (getSupportFragmentManager().findFragmentByTag("step") == null) {
                ft.addToBackStack("step");
            }
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
