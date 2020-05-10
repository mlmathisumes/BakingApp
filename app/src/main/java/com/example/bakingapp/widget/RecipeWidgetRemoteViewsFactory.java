package com.example.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.R;
import com.example.bakingapp.RecipeWidgetRemoteViewService;
import com.example.bakingapp.data.Ingredient;
import com.example.bakingapp.data.RecipeContract;
import com.example.bakingapp.provider.RecipeContentProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.bakingapp.widget.RecipeIngredientsWidget.INGREDIENT_LIST;
import static com.example.bakingapp.widget.RecipeIngredientsWidget.ingredientText;

public class RecipeWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;
    private String ingredients;
    private int ingredientsIndex;
    private List<Ingredient> ingredientArrayList;
    public static final String TAG = RecipeWidgetRemoteViewService.class.getSimpleName();

    public RecipeWidgetRemoteViewsFactory(Context mAContext, Intent intent) {
        Log.d(TAG, "RecipeWidgetRemoteViewFactory");
        this.mContext = mAContext;
        Log.d(TAG, "RWRVF created");
        ingredients = intent.getStringExtra(INGREDIENT_LIST);

        ingredientArrayList = parseIngredientJSON(ingredients);
        Log.d(TAG, "size of list " + ingredientArrayList.size());
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");


        /*Uri uri = RecipeContentProvider.URI_MENU;
        mCursor = mContext.getContentResolver().query(uri,
                new String[]{RecipeContract.COLUMN_ID, RecipeContract.COLUMN_NAME, RecipeContract.COLUMN_INGREDIENTS},
                //null,
                null, null, null);
        Log.d(TAG, "Uri: " + uri.getPath());
        Log.d(TAG, "Uri: " + uri.getAuthority());



        if(mCursor == null){
            Log.d(TAG, "Cursor == null");

        }

        if(mCursor != null){
            Log.d(TAG, "mCursor not == to null");
        }*/
    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged");
        ingredients = ingredientText;
        ingredientArrayList = parseIngredientJSON(ingredients);
        /*if (mCursor != null) {
            Log.d(TAG, "mCursor = null. Closing cursor ");
            mCursor.close();
        }

        //Uri uri = RecipeContract.CONTENT_URI;
        Uri uri = RecipeContentProvider.URI_MENU;
        mCursor = mContext.getContentResolver().query(uri,
                new String[]{RecipeContract.COLUMN_ID, RecipeContract.COLUMN_NAME, RecipeContract.COLUMN_INGREDIENTS},
                null, null, null);
        mCursor.moveToFirst();
        ingredientsIndex = mCursor.getColumnIndex(RecipeContract.COLUMN_INGREDIENTS);
        ingredients = mCursor.getString(ingredientsIndex);
        ingredientArrayList = parseIngredientJSON(ingredients);

         */



    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }

    }

    @Override
    public int getCount() {
        return ingredientArrayList == null ? 0 : ingredientArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (i == AdapterView.INVALID_POSITION) {
            Log.d(TAG, "Invalid position");
            return null;
        }
        Log.d(TAG, "getViewAt at position: " + i);


        Ingredient ingredient = ingredientArrayList.get(i);

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        rv.setTextViewText(R.id.quantity, String.valueOf(ingredient.getQuantity()));
        rv.setTextViewText(R.id.measure, ingredient.getMeasure());
        rv.setTextViewText(R.id.ingredient, ingredient.getIngredient());

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        //return mCursor.moveToPosition(i) ? mCursor.getLong(0) : i;
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private List<Ingredient> parseIngredientJSON(String jsonString) {
        Gson gson = new Gson();
        Type typ = new TypeToken<List<Ingredient>>() {
        }.getType();
        List<Ingredient> ingredientList = gson.fromJson(jsonString, typ);
        return ingredientList;
    }
}
