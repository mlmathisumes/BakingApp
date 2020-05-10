package com.example.bakingapp.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.bakingapp.data.Step;
import com.example.bakingapp.ui.StepFragment;
import com.example.bakingapp.ui.StepVideoFragment;

import java.util.ArrayList;

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Step> stepArrayList;
    public MyViewPagerAdapter(FragmentManager supportFragmentManager, ArrayList<Step> stepArrayList) {
        super(supportFragmentManager);
        this.stepArrayList = stepArrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Step step = stepArrayList.get(position);
        if(!step.getVideoURL().equals("") && step.getVideoURL() != null) {
            return StepVideoFragment.newInstance(step.getVideoURL(), step.getDescription());

        }else{
            return StepFragment.newInstance(step.getDescription());
        }
    }


    @Override
    public int getCount() {
        return stepArrayList.size();
    }
}
