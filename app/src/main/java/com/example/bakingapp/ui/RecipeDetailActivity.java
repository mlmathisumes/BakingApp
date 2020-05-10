package com.example.bakingapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.alespero.expandablecardview.ExpandableCardView;
import com.example.bakingapp.R;
import com.example.bakingapp.adapter.StepsRecyclerViewAdapter;
import com.example.bakingapp.common.SpaceItemDecoration;
import com.example.bakingapp.data.Ingredient;
import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.Step;
import com.example.bakingapp.databinding.ActivityRecipeDetailBinding;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements StepsRecyclerViewAdapter.StepAdapterOnItemClick {

    public static final String TAG = RecipeDetailActivity.class.getSimpleName();
    public static final String RECIPE = "recipe";
    public static final String STEPS = "steps";
    public static final String STEP_ID = "step";

    TextView tv_ingredients;
    StepsRecyclerViewAdapter stepsRecyclerViewAdapter;
    Recipe recipe;
    ActivityRecipeDetailBinding activityRecipeDetailBinding;
    StepsRecyclerViewAdapter.StepAdapterOnItemClick stepAdapterOnItemClick;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityRecipeDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);

        Intent intent = getIntent();
        recipe =  intent.getParcelableExtra(RECIPE);
        List<Ingredient> ingredientsList = recipe.getIngredients();
        List<Step> stepList = recipe.getSteps();

        getSupportActionBar().setTitle(recipe.getName());

        // Tablet view only
        if(findViewById(R.id.frag_container) != null){
            ArrayList<Step> stepsArrayList = new ArrayList<>(recipe.getSteps());
            Step step = stepsArrayList.get(0);
            Fragment fragment;
            if(!step.getVideoURL().equals("") && step.getVideoURL() != null){
                fragment = StepVideoFragment.newInstance(step.getVideoURL(), step.getDescription());
            }else{
                fragment = StepFragment.newInstance(step.getDescription());
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_container, fragment)
                    .commit();

        }

        stepAdapterOnItemClick = this;
        tv_ingredients = findViewById(R.id.tv_ingredients);

        for (Ingredient ingredient : ingredientsList) {
            tv_ingredients.append(" \u2713 " + ingredient.getQuantity() + " " + ingredient.getMeasure() + " "
                    + ingredient.getIngredient() + " \n");
        }

        stepsRecyclerViewAdapter = new StepsRecyclerViewAdapter(getApplicationContext(), stepList, stepAdapterOnItemClick);

        if(stepList != null){
            activityRecipeDetailBinding.rcStep.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            activityRecipeDetailBinding.rcStep.addItemDecoration(new SpaceItemDecoration(10));
            activityRecipeDetailBinding.rcStep.setAdapter(stepsRecyclerViewAdapter);
        }else{

        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(getParentActivityIntent()==null){
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
                }

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onItemClick(Step step) {
        ArrayList<Step> stepsArrayList = new ArrayList<>(recipe.getSteps());
        if(findViewById(R.id.frag_container) != null){
            Fragment fragment;
            if(!step.getVideoURL().equals("") && step.getVideoURL() != null){
                fragment = StepVideoFragment.newInstance(step.getVideoURL(), step.getDescription());

            }else{
                fragment = StepFragment.newInstance(step.getDescription());
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_container, fragment)
                    .commit();
        }else{
            Intent intent = new Intent(this, RecipeDetailViewActivity.class);
            intent.putParcelableArrayListExtra(STEPS, stepsArrayList);
            intent.putExtra(STEP_ID, step.getId());
            startActivity(intent);
        }


    }
}
