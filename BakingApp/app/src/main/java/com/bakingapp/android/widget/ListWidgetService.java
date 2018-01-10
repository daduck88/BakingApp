package com.bakingapp.android.widget;

/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bakingapp.android.App;
import com.bakingapp.android.R;
import com.bakingapp.android.data.Recipe;
import com.bakingapp.android.data.Step;
import com.bakingapp.android.detailrecipe.DetailActivity;
import com.bakingapp.android.model.service.repository.RecipesDataRepository;
import com.bakingapp.android.provider.recipe.RecipeCursor;
import com.bakingapp.android.provider.recipe.RecipeSelection;
import com.bakingapp.android.provider.selectedrecipe.SelectedRecipeCursor;
import com.bakingapp.android.provider.selectedrecipe.SelectedRecipeSelection;

import java.util.List;

public class ListWidgetService extends RemoteViewsService {

  public static final String APP_WIDGET_ID = "APP_WIDGET_ID";

  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    int mAppWidgetId;
    mAppWidgetId = intent.getIntExtra(APP_WIDGET_ID,
        AppWidgetManager.INVALID_APPWIDGET_ID);
    return new ListRemoteViewsFactory(this.getApplicationContext(), mAppWidgetId);
  }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

  private final int mAppWidgetId;
  Context mContext;
  List<Step> mStepList;

  public ListRemoteViewsFactory(Context applicationContext, int appWidgetId) {
    mContext = applicationContext;
    this.mAppWidgetId = appWidgetId;
  }

  @Override
  public void onCreate() {

  }

  //called on start and when notifyAppWidgetViewDataChanged is called
  @Override
  public void onDataSetChanged() {
    SelectedRecipeSelection selectedRecipeSelection = new SelectedRecipeSelection();
    selectedRecipeSelection.widgetId(mAppWidgetId);
    SelectedRecipeCursor selectedRecipeCursor = selectedRecipeSelection.query(mContext);
    while(selectedRecipeCursor != null && selectedRecipeCursor.moveToNext()) {
      RecipeSelection recipeSelection = new RecipeSelection();
      recipeSelection.idRecipe(selectedRecipeCursor.getIdRecipe());
      RecipeCursor recipeCursor = recipeSelection.query(App.getContext());
      if((recipeCursor != null && recipeCursor.moveToNext())) {
        Recipe widgetRecipe = RecipesDataRepository.getRecipe(recipeCursor);
        mStepList = widgetRecipe.getSteps();
      }
    }
  }

  @Override
  public void onDestroy() {
  }

  @Override
  public int getCount() {
    if(mStepList == null) {
      return 0;
    }
    return mStepList.size();
  }

  /**
   * This method acts like the onBindViewHolder method in an Adapter
   *
   * @param position The current position of the item in the GridView to be displayed
   * @return The RemoteViews object to display for the provided postion
   */
  @Override
  public RemoteViews getViewAt(int position) {
    Step step = mStepList.get(position);

    RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.item_step);

    views.setTextViewText(R.id.content, step.getShortDescription());

    // Fill in the onClick PendingIntent Template using the specific step position
    Bundle extras = new Bundle();
    extras.putLong(DetailActivity.CURRENT_STEP, position);
    Intent fillInIntent = new Intent();
    fillInIntent.putExtras(extras);
    views.setOnClickFillInIntent(R.id.layoutListener, fillInIntent);

    return views;
  }

  @Override
  public RemoteViews getLoadingView() {
    return null;
  }

  @Override
  public int getViewTypeCount() {
    return 1; // Treat all items in the GridView the same
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @Override
  public boolean hasStableIds() {
    return true;
  }
}

