package com.example.bakingapp.data;

import android.content.ContentResolver;
import android.net.Uri;

public class RecipeContract {
    public static final String AUTHORITY =
            "com.example.bakingapp";

    public static final Uri BASE_CONTENT_URI =
            Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "recipes" directory
    public static final String PATH_RECIPES = "recipes";


    /**
     * This is the {@link Uri} used to get a full list of recipes.
     */
    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).build();

    public static final int DATABASE_VERSION = 1;

    /**
     * This is a String type that denotes a Uri references a list or directory.
     */
    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_RECIPES;

    /**
     * This is the name of the SQL database for recipes
     */
    public static final String DATABASE_NAME = "recipes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_INGREDIENTS = "ingredients";


    public static final String[] COLUMNS = {COLUMN_ID, COLUMN_NAME};

    public static final int COLUMN_INDEX_ID = 0;
    public static final int COLUMN_INDEX_NAME = 1;
    public static final int COLUMN_INDEX_INGREDIENTS = 2;



}
