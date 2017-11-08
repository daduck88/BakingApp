package com.bakingapp.android;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bakingapp.android.base.BaseActivity;
import com.bakingapp.android.data.Recipe;
import com.bakingapp.android.detailrecipe.DetailActivity;
import com.bakingapp.android.recipies.RecipesFragment;

public class MainActivity extends BaseActivity implements RecipesFragment.OnListFragmentInteractionListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initToolbar();
    initRecipesList();
  }

  private void initRecipesList() {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.main_container, RecipesFragment.newInstance(getResources().getInteger(R.integer.recipes_columns)));
    ft.commitAllowingStateLoss();
  }

  @Override
  public void onListFragmentInteraction(Recipe recipe) {
    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
    intent.putExtra(DetailActivity.RECIPE, recipe);
    startActivity(intent);
  }
}
