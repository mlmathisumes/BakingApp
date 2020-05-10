package com.example.bakingapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeRecyclerViewAdapter extends
        RecyclerView.Adapter<RecipeRecyclerViewAdapter.RecipeRecyclerViewHolder> {

    private List<Recipe> mRecipeList;
    private Context context;
    private LayoutInflater inflater;
    private RecipeAdapterOnItemClick mClickHandler;


    public RecipeRecyclerViewAdapter(Context context, List<Recipe> mRecipeList, RecipeAdapterOnItemClick mClickHandler) {
        this.mRecipeList = mRecipeList;
        this.context = context;
        this.mClickHandler = mClickHandler;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecipeRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.recipe_item, parent, false);
        return new RecipeRecyclerViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeRecyclerViewHolder holder, int position) {
        holder.textView.setText(mRecipeList.get(position).getName());
        if(mRecipeList.get(position).getImage().equals("")|| mRecipeList.get(position) == null){
            holder.imageView.setImageResource(R.drawable.baking);
        }
        else{
            // Load image using glide
        }
        holder.bindRecipe(position);

    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();

    }

    public interface RecipeAdapterOnItemClick{
        void onItemClick(Recipe recipe);
    }


    public class RecipeRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView;
        ImageView imageView;
        Recipe recipe;

        public RecipeRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recipeName);
            imageView = itemView.findViewById(R.id.recipeImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onItemClick(recipe);
        }

        void bindRecipe(int index){
          recipe = mRecipeList.get(index);
        }
    }
}
