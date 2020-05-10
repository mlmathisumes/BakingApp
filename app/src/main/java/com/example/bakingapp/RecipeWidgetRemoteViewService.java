package com.example.bakingapp;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.bakingapp.widget.RecipeWidgetRemoteViewsFactory;

public class RecipeWidgetRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetRemoteViewsFactory(this.getApplicationContext(),intent);
    }
}
