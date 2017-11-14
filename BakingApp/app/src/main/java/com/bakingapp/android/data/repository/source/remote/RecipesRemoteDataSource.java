package com.bakingapp.android.data.repository.source.remote;

import android.support.annotation.NonNull;

import com.bakingapp.android.data.Recipe;
import com.bakingapp.android.data.repository.source.RecipesDataSource;

/**
 * Created by destevancardozoj on 11/8/17.
 */

public class RecipesRemoteDataSource implements RecipesDataSource {
  @Override
  public void getRecipes(@NonNull LoadRecipesCallback callback) {

  }

  @Override
  public void getRecipe(@NonNull String recipeId, @NonNull GetRecipeCallback callback) {

  }

  @Override
  public void saveRecipe(@NonNull Recipe recipe) {

  }

  @Override
  public void refreshRecipes() {

  }
}
