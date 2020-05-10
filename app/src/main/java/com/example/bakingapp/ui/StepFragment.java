package com.example.bakingapp.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapp.R;
import com.example.bakingapp.databinding.FragmentStepBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepFragment extends Fragment {
    private static final String DESCRIPTION = "description";
    private FragmentStepBinding fragmentStepBinding;

    private String step_description;

    public StepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param description Parameter 1.
     * @return A new instance of fragment StepFragment.
     */
    public static StepFragment newInstance(String description) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        args.putString(DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step_description = getArguments().getString(DESCRIPTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentStepBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step, container, false);
        return fragmentStepBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //tv_stepDescription.setText(step_description);
        fragmentStepBinding.stepDescription.setText(step_description);
    }
}
