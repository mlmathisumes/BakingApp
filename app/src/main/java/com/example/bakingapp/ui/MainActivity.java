package com.example.bakingapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.test.espresso.IdlingResource;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.bakingapp.GetRecipesService;
import com.example.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.bakingapp.R;
import com.example.bakingapp.adapter.RecipeRecyclerViewAdapter;
import com.example.bakingapp.common.Common;
import com.example.bakingapp.common.SpaceItemDecoration;
import com.example.bakingapp.data.Ingredient;
import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.RecipeContract;
import com.example.bakingapp.databinding.ActivityMainBinding;
import com.example.bakingapp.provider.RecipeContentProvider;
import com.example.bakingapp.utils.AppExecutors;
import com.example.bakingapp.utils.RetrofitInstance;
import com.example.bakingapp.viewmodel.MainViewModelFactory;
import com.example.bakingapp.viewmodel.RecipeListViewModel;

import java.util.List;


public class MainActivity extends AppCompatActivity implements LifecycleOwner, RecipeRecyclerViewAdapter.RecipeAdapterOnItemClick {

    public static final String TAG = MainActivity.class.getSimpleName();

    private GetRecipesService getRecipesService;
    private ActivityMainBinding activityMainBinding;
    private RecipeListViewModel recipeListViewModel;
    private RecipeRecyclerViewAdapter recipeRecyclerViewAdapter;
    private RecipeRecyclerViewAdapter.RecipeAdapterOnItemClick mClickHandler;

    // The Idling Resource which will be null in production
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIldingResource(){
        if(mIdlingResource == null){
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        mClickHandler = this;

        getRecipesService = RetrofitInstance.getRetrofitInstance().create(GetRecipesService.class);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.pbLoadingIndicator.setVisibility(View.VISIBLE);

        MainViewModelFactory factory = InjectUtils.provideMainActivityViewModelFactory(getApplication(), mIdlingResource);
        recipeListViewModel = new ViewModelProvider(this,factory).get(RecipeListViewModel.class);

        checkOrientation();

        recipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if(recipes != null){
                    activityMainBinding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
                    activityMainBinding.rvRecipeList.setVisibility(View.VISIBLE);
                    recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(getApplicationContext(), recipes, mClickHandler);
                    activityMainBinding.rvRecipeList.setAdapter(recipeRecyclerViewAdapter);
                }else{
                    activityMainBinding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
                    activityMainBinding.emptyRcMessage.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void checkOrientation() {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            activityMainBinding.rvRecipeList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            activityMainBinding.rvRecipeList.addItemDecoration(new SpaceItemDecoration(4));

        }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            activityMainBinding.rvRecipeList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            activityMainBinding.rvRecipeList.addItemDecoration(new SpaceItemDecoration(6));

        }
    }
    @Override
    public void onItemClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(Common.RECIPE_INTENT_EXTRA, recipe);
        startActivity(intent);
    }

}
