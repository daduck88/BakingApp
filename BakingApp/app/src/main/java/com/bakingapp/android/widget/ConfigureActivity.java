package com.bakingapp.android.widget;

import android.appwidget.AppWidgetManager;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.bakingapp.android.App;
import com.bakingapp.android.MainActivity;
import com.bakingapp.android.R;
import com.bakingapp.android.data.Recipe;
import com.bakingapp.android.provider.selectedrecipe.SelectedRecipeContentValues;

/**
 * Created by destevancardozoj on 12/28/17.
 */

public class ConfigureActivity extends MainActivity {
  private int mAppWidgetId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setResult(RESULT_CANCELED, null);
  }

  @Override
  public void onRecipeClick(Recipe recipe, View view) {
    initAppWidgetId();
    saveSelectedRecipe(recipe.getId());
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ConfigureActivity.this);
    RemoteViews views = new RemoteViews(getPackageName(),
        R.layout.recipe_widget);
    appWidgetManager.updateAppWidget(mAppWidgetId, views);

    Intent resultValue = new Intent();
    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
    setResult(RESULT_OK, resultValue);
    finish();
  }

  private void saveSelectedRecipe(Integer recipeId) {
    SelectedRecipeContentValues values = new SelectedRecipeContentValues();
    values.putWidgetId(mAppWidgetId);
    values.putIdRecipe(recipeId);
    Uri uri = values.insert(App.getContext());
    long selectedRecipeDBID = ContentUris.parseId(uri);
    Log.e("TEMP ", "SELECTED RECIPE inserted " + selectedRecipeDBID);
  }

  private void initAppWidgetId() {
    mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    Intent intent = getIntent();
    Bundle extras = intent.getExtras();
    if(extras != null) {
      mAppWidgetId = extras.getInt(
          AppWidgetManager.EXTRA_APPWIDGET_ID,
          AppWidgetManager.INVALID_APPWIDGET_ID);
    }
  }
}
