package com.example.bakingapp.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.databinding.FragmentStepBinding;
import com.example.bakingapp.databinding.FragmentStepVideoBinding;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.w3c.dom.Text;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepVideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepVideoFragment extends Fragment implements ExoPlayer.EventListener {
    public static final String TAG = StepVideoFragment.class.getSimpleName();

    private static final String VIDEO_URL = "videoUrl";
    private static final String DESCRIPTION = "description";

    private SimpleExoPlayer mExoPlayer;
    private FragmentStepVideoBinding fragmentStepVideoBinding;
    private String videoUrl;
    private String stepDescription;

    public StepVideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param videoUrl Parameter 1.
     * @param stepDescription Parameter 2.
     * @return A new instance of fragment VideoFragment.
     */
    public static StepVideoFragment newInstance(String videoUrl, String stepDescription) {
        StepVideoFragment fragment = new StepVideoFragment();
        Bundle args = new Bundle();
        args.putString(VIDEO_URL, videoUrl);
        args.putString(DESCRIPTION, stepDescription);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializePlayer(Uri.parse(videoUrl));
        fragmentStepVideoBinding.stepDescription.setText(stepDescription);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videoUrl = getArguments().getString(VIDEO_URL);
            stepDescription = getArguments().getString(DESCRIPTION);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentStepVideoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_video, container, false);
        return fragmentStepVideoBinding.getRoot();

    }

    private void initializePlayer(Uri uri){
        if(mExoPlayer == null){

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            fragmentStepVideoBinding.playerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);


            DataSource.Factory dataSourceFactory =
                    new DefaultDataSourceFactory(getContext(), "baking-app");
            MediaSource mediaSource = new ExtractorMediaSource(uri, dataSourceFactory,
                    new DefaultExtractorsFactory(), null, null);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    private boolean isMobileLandscape(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void hideUI(){
        Configuration configuration = getResources().getConfiguration();
        if(isMobileLandscape() && configuration.smallestScreenWidthDp <= 600){
            fragmentStepVideoBinding.stepDescription.setVisibility(View.GONE);

            fragmentStepVideoBinding.playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }


    @Override
    public void onResume() {
        super.onResume();
        hideUI();
        if(Util.SDK_INT <= 23 || mExoPlayer == null){
            initializePlayer(Uri.parse(videoUrl));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    private void releasePlayer() {
        if(mExoPlayer != null){
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            Log.d(TAG, "onPlayerStateChanged: PLAYING");
        } else if(playbackState == ExoPlayer.STATE_READY){
            Log.d(TAG, "onPlayerStateChanged: PAUSED");

        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}
