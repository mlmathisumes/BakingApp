package com.example.bakingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bakingapp.data.RecipeRepository;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final RecipeRepository recipeRepository;
    private final Application application;


    public MainViewModelFactory(Application application, RecipeRepository recipeRepository) {
        this.application = application;
        this.recipeRepository = recipeRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeListViewModel(application, recipeRepository);
    }
}
