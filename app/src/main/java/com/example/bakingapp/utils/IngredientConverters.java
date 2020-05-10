package com.example.bakingapp.utils;


import androidx.room.TypeConverter;

import com.example.bakingapp.data.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class IngredientConverters {

    @TypeConverter
    public String toIngredientString(List<Ingredient> ingredients){
        if(ingredients == null) {
            return null;
        }

        Gson gson = new Gson();
        return gson.toJson(ingredients);

    }

    @TypeConverter
    public List<Ingredient> toIngredientList(String ingred){
        if(ingred == null){
            return null;
        }

        Type listType = new TypeToken<List<Ingredient>>(){}.getType();
        List<Ingredient> ingredients = new Gson().fromJson(ingred, listType);
            return ingredients;

    }

}
