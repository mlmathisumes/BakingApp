package com.example.bakingapp;

import retrofit2.Call;
import retrofit2.http.GET;

import com.example.bakingapp.data.Recipe;

import java.util.List;

public interface GetRecipesService {

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();


}