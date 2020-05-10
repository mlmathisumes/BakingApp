package com.example.bakingapp.ui;

import android.app.Application;
import android.content.Context;

import androidx.test.espresso.IdlingResource;

import com.example.bakingapp.GetRecipesService;
import com.example.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.RecipeRepository;
import com.example.bakingapp.utils.AppExecutors;
import com.example.bakingapp.utils.RetrofitInstance;
import com.example.bakingapp.viewmodel.MainViewModelFactory;

public class InjectUtils {

    private static RecipeRepository provideRepository(Context context, SimpleIdlingResource idlingResource){
        GetRecipesService getRecipesService = RetrofitInstance.getRetrofitInstance().create(GetRecipesService.class);
        AppExecutors appExecutors = AppExecutors.getInstance();
        return RecipeRepository.getInstance(context, getRecipesService, appExecutors, idlingResource);
    }

    public static MainViewModelFactory provideMainActivityViewModelFactory(Application application, SimpleIdlingResource idlingResource){
        RecipeRepository recipeRepository = provideRepository(application.getApplicationContext(), idlingResource);
        return new MainViewModelFactory(application, recipeRepository);
    }
}
