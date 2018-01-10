package com.bakingapp.android.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.bakingapp.android.R;
import com.bakingapp.android.detailrecipe.DetailActivity;

/**
 * Created by destevancardozoj on 12/28/17.
 */

public class RecipeWidgetProvider extends AppWidgetProvider {

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                              int appWidgetId) {
    RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
    Intent intent = new Intent(context, ListWidgetService.class);
    intent.putExtra(ListWidgetService.APP_WIDGET_ID, appWidgetId);
    rv.setRemoteAdapter(R.id.lVWidget, intent);
    // Set the PlantDetailActivity intent to launch when clicked
    Intent appIntent = new Intent(context, DetailActivity.class);
    PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    rv.setPendingIntentTemplate(R.id.lVWidget, appPendingIntent);
    // Handle empty gardens
    rv.setEmptyView(R.id.lVWidget, R.id.emptyView);
    appWidgetManager.updateAppWidget(appWidgetId, rv);
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    for(int appWidgetId : appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId);
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  @Override
  public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId, Bundle newOptions) {
    updateAppWidget(context, appWidgetManager, appWidgetId);
    super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
  }
}
