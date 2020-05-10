package com.example.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.bakingapp.R;
import com.example.bakingapp.RecipeWidgetRemoteViewService;
import com.example.bakingapp.data.Ingredient;
import com.example.bakingapp.data.RecipeContract;
import com.example.bakingapp.provider.RecipeContentProvider;
import com.example.bakingapp.utils.AppExecutors;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientsWidget extends AppWidgetProvider {

    public static final String TAG = RecipeIngredientsWidget.class.getSimpleName();
    public static final String INGREDIENT_LIST = "INGREDIENT_LIST";
    public static final String ACTION_NEXT_RECIPE = "ACTION_NEXT_RECIPE";
    public static final String ACTION_PREV_RECIPE = "ACTION_REV_RECIPE";
    public static String ingredientText;
    public static int position = 0;
    private static ArrayList<Ingredient> ingredientArrayList;
    private static Cursor mCursor;
    private static int recipeCount;
    private static int nameIndex;
    private static int ingredientsIndex;


    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {
        Log.d(TAG, "updateAppWidget");

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                // Construct the RemoteViews object
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredients_widget);
                Intent intent = new Intent(context, RecipeWidgetRemoteViewService.class);

                if(mCursor == null){
                    Uri uri = RecipeContentProvider.URI_MENU;
                    mCursor = context.getContentResolver().query(uri,
                            new String[]{RecipeContract.COLUMN_ID, RecipeContract.COLUMN_NAME, RecipeContract.COLUMN_INGREDIENTS},
                            null, null, null);

                    nameIndex = mCursor.getColumnIndex(RecipeContract.COLUMN_NAME);
                    ingredientsIndex = mCursor.getColumnIndex(RecipeContract.COLUMN_INGREDIENTS);
                   recipeCount = mCursor.getCount();
                }
                mCursor.moveToPosition(position);

                String name = mCursor.getString(nameIndex);
                 ingredientText = mCursor.getString(ingredientsIndex);

                Intent intentPrevious = new Intent(context, RecipeIngredientsWidget.class);
                intentPrevious.setAction(ACTION_PREV_RECIPE);
                PendingIntent pendingIntentPrevious = PendingIntent.getBroadcast(context, 0, intentPrevious, 0);
                views.setOnClickPendingIntent(R.id.btn_previous_recipe, pendingIntentPrevious);

                Intent intentNext = new Intent(context, RecipeIngredientsWidget.class);
                intentNext.setAction(ACTION_NEXT_RECIPE);
                PendingIntent pendingIntentNext = PendingIntent.getBroadcast(context, 0, intentNext, 0);
                views.setOnClickPendingIntent(R.id.btn_next_recipe, pendingIntentNext);


                intent.putExtra(INGREDIENT_LIST, ingredientText);

                views.setRemoteAdapter(R.id.widgetListView, intent);
                views.setTextViewText(R.id.recipeName, name);

                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        });


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d(TAG, "onUpdate");

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");

        if(intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)){
            AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
            int[] ids = widgetManager.getAppWidgetIds(new ComponentName(context, RecipeIngredientsWidget.class));
            onUpdate(context, widgetManager, ids);

        }else if(intent.getAction().equals(ACTION_NEXT_RECIPE)){
            if(position + 1 >= recipeCount){
                position = 0;
            }else {
                position++;
            }

            AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
            int[] ids = widgetManager.getAppWidgetIds(new ComponentName(context, RecipeIngredientsWidget.class));
            widgetManager.notifyAppWidgetViewDataChanged(ids, R.id.widgetListView);
            onUpdate(context, widgetManager, ids);

        }else if(intent.getAction().equals(ACTION_PREV_RECIPE)){
            if((position - 1) >  - 1){
                position--;
            }else{
                position = recipeCount - 1;
            }
            AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
            int[] ids = widgetManager.getAppWidgetIds(new ComponentName(context, RecipeIngredientsWidget.class));
            widgetManager.notifyAppWidgetViewDataChanged(ids, R.id.widgetListView);
            onUpdate(context, widgetManager, ids);
        }
    }
}

