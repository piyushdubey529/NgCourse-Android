package com.ngcourse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.ngcourse.Settings.Keys;
import com.ngcourse.beans.Video;
import com.ngcourse.utilities.FontAwesome;

import java.util.ArrayList;

/**
 * Created by piyush on 31/5/17.
 */

public class FragmentPlayYoutubeVideo extends Fragment implements View.OnClickListener {

    private FragmentActivity mContext;
    private YouTubePlayer YPlayer;
    private int  position;
    private ArrayList<Video> videoList;
    private TextView videoName;
    private TextView technology;
    private TextView topic;
    private Video video;
    private FontAwesome shareButton;
    private LinearLayout searchLayout;
    private EditText searchInput;
    private FontAwesome filter;
    private FontAwesome backButton;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FragmentActivity) {
            mContext = (FragmentActivity) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_video_layout, container, false);
        getBundleData();
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction =getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();
        youTubePlayerFragment.initialize(Keys.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    YPlayer = youTubePlayer;
                    YPlayer.setFullscreen(false);

/*
                    YPlayer.loadVideo("2zNSgSzhBfM");
*/
                    YPlayer.cueVideo(video.getYoutubeVideoId());
                    /*YPlayer.play();*/
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
        return view;
    }


    private void setView() {
      videoName.setText(video.getVideoName());
        technology.setText(video.getTechnology());
        topic.setText(video.getTopic());
    }

    private void initView() {
        videoName = (TextView) mContext.findViewById(R.id.videoName);
        technology = (TextView) mContext.findViewById(R.id.technology);
        topic = (TextView) mContext.findViewById(R.id.topic);
        shareButton = (FontAwesome) mContext.findViewById(R.id.share);
        searchLayout = (LinearLayout) mContext.findViewById(R.id.searchLayout);
        searchInput = (EditText) mContext.findViewById(R.id.searchInput);
        filter = (FontAwesome) mContext.findViewById(R.id.filter_icon);
        backButton = (FontAwesome) mContext.findViewById(R.id.backButton);
        searchLayout.setVisibility(View.GONE);
        filter.setVisibility(View.GONE);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        initView();
        setView();
        setListener();
    }

    private void setListener() {
        shareButton.setOnClickListener(this);
    }

    public void getBundleData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt("position", -1);
            videoList = bundle.getParcelableArrayList("videoList");
            video = videoList.get(position);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share:
                shareVideo();
                break;
        }
    }

    private void shareVideo() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, video.getVideoUrl());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Send to"));
    }
}
