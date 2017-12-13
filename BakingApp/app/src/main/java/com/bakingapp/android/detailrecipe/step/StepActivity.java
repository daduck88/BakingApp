package com.bakingapp.android.detailrecipe.step;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overrideOrientation = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initData();
        initToolbar();
        navigateToStep(mCurrentStepPosition);
    }

    private void initData() {
        mRecipe = getIntent().getExtras().getParcelable(RECIPE);
        mCurrentStepPosition = getIntent().getExtras().getInt(STEP_NUMBER);
    }

    @Override
    public void onStepClick(int position, View view) {
        navigateToStep(position);
    }

    private void navigateToStep(int selectedStepPosition) {
        Step currentStep = mRecipe.getSteps().get(selectedStepPosition);
        mCurrentStepPosition = selectedStepPosition;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        boolean isFirstStep = selectedStepPosition == 0;
        boolean isLastStep = selectedStepPosition == (mRecipe.getSteps().size() - 1);
        StepFragment stepFragment = StepFragment.newInstance(currentStep, isFirstStep, isLastStep);
        stepFragment.setListener(this);
        ft.replace(R.id.main_container, stepFragment, "step");
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
