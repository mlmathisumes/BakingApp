package com.example.bakingapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.database.AppDatabase;
import com.example.bakingapp.database.RecipeDao;

public class RecipeContentProvider extends ContentProvider {

    /**
     * The autority of this content provider.
     */
    public static final String AUTHORITY = "com.example.bakingapp.provider";

    /**
     * The URI for the Recipe table.
     */
    public static final Uri URI_MENU = Uri.parse(
            "content://" + AUTHORITY + "/" + Recipe.TABLE_NAME);

    private static final int CODE_RECIPE_DIR = 1;

    private static final int CODE_MENU_ITEM = 2;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);


    static {
        MATCHER.addURI(AUTHORITY, Recipe.TABLE_NAME, CODE_RECIPE_DIR);
        MATCHER.addURI(AUTHORITY, Recipe.TABLE_NAME + "/*", CODE_MENU_ITEM);
    }


    /**
     * Defines a handle to the Room database
     */
    private AppDatabase appDatabase;

    /**
     * Defines a Data Access Object to perform the database operations
     */
    private RecipeDao recipeDao;

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        if(getContext() != null){
            // Creates a new database object.
            appDatabase = AppDatabase.getInstance(getContext());
            // Gets a Data Access Object to perform the database operations
            recipeDao = appDatabase.recipeDao();

            final Cursor cursor = recipeDao.getRecipesWithCursor();
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }
        throw  new IllegalArgumentException("Failed to query " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_RECIPE_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + Recipe.TABLE_NAME;
            case CODE_MENU_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + Recipe.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
