package com.bakingapp.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.bakingapp.android.base.BaseActivity;
import com.bakingapp.android.data.Recipe;
import com.bakingapp.android.detailrecipe.DetailActivity;
import com.bakingapp.android.recipes.RecipesAdapter;
import com.bakingapp.android.recipes.RecipesFragment;

public class MainActivity extends BaseActivity implements RecipesAdapter.RecipeClickListener {

  RecipesFragment mRecipesFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initToolbar();
    initRecipesList();
  }

  private void initRecipesList() {
    FragmentManager fm = getSupportFragmentManager();
    mRecipesFragment = (RecipesFragment) fm.findFragmentByTag("recipes");
    if(mRecipesFragment == null) {
      FragmentTransaction ft = fm.beginTransaction();
      mRecipesFragment = RecipesFragment.newInstance(getResources().getInteger(R.integer.recipes_columns));
      ft.replace(R.id.main_container, mRecipesFragment, "recipes");
      ft.commitAllowingStateLoss();
    }
  }

  @Override
  public void onRecipeClick(Recipe recipe, View view) {
    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
    intent.putExtra(DetailActivity.RECIPE, recipe);
    startActivity(intent);
  }

  @VisibleForTesting
  public CountingIdlingResource getEspressoIdlingResourceForMainActivity() {
    return mRecipesFragment.getEspressoIdlingResourceForRecipesFragment();
  }
}
