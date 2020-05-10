package com.example.bakingapp.database;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.bakingapp.data.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipes")
    LiveData<List<Recipe>> loadRecipes();

    @Query("SELECT * FROM recipes WHERE id = :id")
    LiveData<Recipe> loadRecipesById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Recipe> recipes);

    @Query("SELECT * FROM recipes")
    Cursor getRecipesWithCursor();
}
