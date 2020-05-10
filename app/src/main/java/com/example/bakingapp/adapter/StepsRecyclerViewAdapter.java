package com.example.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.Step;

import java.util.List;

public class StepsRecyclerViewAdapter extends RecyclerView.Adapter<StepsRecyclerViewAdapter.StepsRecyclerViewHolder> {

    private List<Step> stepList;
    private Context context;
    private LayoutInflater inflater;
    private final StepAdapterOnItemClick mClickHandler;

    public StepsRecyclerViewAdapter(Context context, List<Step> steps, StepAdapterOnItemClick mClickHandler) {
        this.stepList = steps;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mClickHandler = mClickHandler;
    }


    @NonNull
    @Override
    public StepsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.step_item, parent, false);
        return new StepsRecyclerViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsRecyclerViewHolder holder, int position) {
        holder.textView.setText(stepList.get(position).getShortDescription());
        holder.bindStep(position);
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public interface StepAdapterOnItemClick{
        void onItemClick(Step step);
    }

    public class StepsRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        Step step;

        public StepsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.step_description);
            itemView.setOnClickListener(this);
        }

        void bindStep(int index){
            step = stepList.get(index);
        }


        @Override
        public void onClick(View view) {
            mClickHandler.onItemClick(step);

        }
    }
}
