package com.example.bakingapp.data;

import android.content.Context;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.bakingapp.GetRecipesService;
import com.example.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.bakingapp.database.AppDatabase;
import com.example.bakingapp.database.RecipeDao;
import com.example.bakingapp.utils.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {

    private RecipeDao mRecipeDao;
    private LiveData<List<Recipe>> mAllRecipes;
    private LiveData<List<Recipe>> dbRecipes;
    private GetRecipesService getRecipesService;
    private AppExecutors appExecutors;
    public static RecipeRepository instance;

    public static final String TAG = RecipeRepository.class.getSimpleName();

    private RecipeRepository(Context context, GetRecipesService getRecipesService, AppExecutors appExecutors, final SimpleIdlingResource idlingResource){
        AppDatabase db = AppDatabase.getInstance(context);
        this.mRecipeDao = db.recipeDao();
        this.getRecipesService = getRecipesService;
        this.appExecutors = appExecutors;

        getAllRecipesFromDB();
        // mAllRecipes = fetchRecipes();

        mAllRecipes = Transformations.map(dbRecipes, new Function<List<Recipe>, List<Recipe>>() {
            @Override
            public List<Recipe> apply(List<Recipe> input) {
                if(input.isEmpty() || input == null){
                    if(idlingResource != null){
                        idlingResource.setIdleState(false);
                    }
                    return fetchRecipes(input);
                }else if(input != null){
                    return input;
                }else{
                    return null;
                }
            }
        });

    }

    public synchronized static RecipeRepository getInstance(Context context, GetRecipesService getRecipesService, AppExecutors appExecutors, SimpleIdlingResource idlingResource){
        if(instance == null){
            instance = new RecipeRepository(context , getRecipesService, appExecutors, idlingResource);
            Log.d(TAG, "Create a new RecipeRepository");
        }
        return instance;
    }

    public void getAllRecipesFromDB(){
        dbRecipes = mRecipeDao.loadRecipes();
    }

    public LiveData<List<Recipe>> getAllRecipes(){
        return  mAllRecipes;
    }


    private List<Recipe> fetchRecipes(final List<Recipe> rc) {
        Call<List<Recipe>> call = getRecipesService.getRecipes();
        call.enqueue(new Callback<List<Recipe>>(){
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                 rc.addAll(response.body());
                 appExecutors.diskIO().execute(new Runnable() {
                     @Override
                     public void run() {
                         //mRecipeDao.insert(apiRecipes.getValue());
                         mRecipeDao.insert(rc);

                     }
                 });
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        return rc;
    }


}
