package com.example.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.bakingapp.R;
import com.example.bakingapp.adapter.MyViewPagerAdapter;
import com.example.bakingapp.common.Common;
import com.example.bakingapp.data.Step;
import com.example.bakingapp.databinding.ActivityRecipeDetailStepsBinding;

import java.util.ArrayList;

public class RecipeDetailViewActivity extends AppCompatActivity {
    private int currentStep;
    private ArrayList<Step> stepArrayList;
    public static final String STEPS = "steps";
    public static final String STEP_ID = "step";
    private ActivityRecipeDetailStepsBinding activityRecipeDetailStepsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityRecipeDetailStepsBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail_steps);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            stepArrayList = bundle.getParcelableArrayList(STEPS);
            currentStep = bundle.getInt(STEP_ID);
        }

        // Set viewPager navigation buttons
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            activityRecipeDetailStepsBinding.btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentStep >= 0 && currentStep < stepArrayList.size()) {
                        currentStep++;
                        activityRecipeDetailStepsBinding.viewPager.setCurrentItem(currentStep);
                    }
                }
            });

            activityRecipeDetailStepsBinding.btnPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentStep > 0 && currentStep < stepArrayList.size()) {
                        currentStep--;
                        activityRecipeDetailStepsBinding.viewPager.setCurrentItem(currentStep);
                    }
                }
            });
        }


        activityRecipeDetailStepsBinding.viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), stepArrayList));
        activityRecipeDetailStepsBinding.viewPager.setOffscreenPageLimit(stepArrayList.size());
        activityRecipeDetailStepsBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateButtons(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        activityRecipeDetailStepsBinding.viewPager.setCurrentItem(currentStep);
        updateButtons(currentStep);
    }

    /**
     * Called when the user swipes/clicks through the viewpager.
     *
     * @param pos The viewPager current position
     */
    private void updateButtons(int pos) {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            return;
        }

        if (pos == 0) {
            activityRecipeDetailStepsBinding.btnPrev.setEnabled(false);
            activityRecipeDetailStepsBinding.btnPrev.setBackgroundResource(android.R.color.darker_gray);

        } else {
            activityRecipeDetailStepsBinding.btnPrev.setEnabled(true);
            activityRecipeDetailStepsBinding.btnPrev.setBackgroundResource(R.color.colorButton);
        }

        if (pos == stepArrayList.size() - 1) {
            activityRecipeDetailStepsBinding.btnNext.setEnabled(false);
            activityRecipeDetailStepsBinding.btnNext.setBackgroundResource(android.R.color.darker_gray);

        } else {
            activityRecipeDetailStepsBinding.btnNext.setEnabled(true);
            activityRecipeDetailStepsBinding.btnNext.setBackgroundResource(R.color.colorButton);

        }
    }
}
