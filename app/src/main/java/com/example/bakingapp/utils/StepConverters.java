package com.example.bakingapp.utils;

import androidx.room.TypeConverter;

import com.example.bakingapp.data.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class StepConverters {

    @TypeConverter
    public String toStepString(List<Step> steps){
        if(steps == null) {
            return null;
        }

        Gson gson = new Gson();
        return gson.toJson(steps);

    }

    @TypeConverter
    public List<Step> toStepList(String value){
        if(value == null){
            return null;
        }

        Type listType = new TypeToken<List<Step>>(){}.getType();
        List<Step> stepList = new Gson().fromJson(value, listType);
        return stepList;

    }
}
