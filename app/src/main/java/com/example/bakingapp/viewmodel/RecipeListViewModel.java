package com.example.bakingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends AndroidViewModel {

    private RecipeRepository recipeRepository;

    public RecipeListViewModel(@NonNull Application application, RecipeRepository recipeRepository) {
        super(application);
        this.recipeRepository = recipeRepository;
    }

    public LiveData<List<Recipe>> getRecipes(){
        return recipeRepository.getAllRecipes();
    }


}
