package com.bakingapp.android.data.repository.source;

import android.support.annotation.NonNull;

import com.bakingapp.android.data.Recipe;

import java.util.List;

/**
 * Created by destevancardozoj on 11/8/17.
 */

public interface RecipesDataSource {

  interface LoadRecipesCallback {

    void onRecipesLoaded(List<Recipe> recipes);

    void onDataNotAvailable();
  }

  interface GetRecipeCallback {

    void onRecipeLoaded(Recipe recipe);

    void onDataNotAvailable();
  }

  void getRecipes(@NonNull LoadRecipesCallback callback);

  void getRecipe(@NonNull String recipeId, @NonNull GetRecipeCallback callback);

  void saveRecipe(@NonNull Recipe recipe);

  void refreshRecipes();

}
