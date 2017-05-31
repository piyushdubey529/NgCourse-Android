package com.ngcourse;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ngcourse.NetworkCall.NetworkCallResponse;
import com.ngcourse.ResponseInterfaces.ResponseVideoList;
import com.ngcourse.Webservices.VideoListApi;
import com.ngcourse.adapter.VideoListAdapter;
import com.ngcourse.beans.Video;

import java.util.ArrayList;

/**
 * Created by piyush on 28/5/17.
 */

public class FragmentVideoList extends Fragment implements View.OnClickListener, NetworkCallResponse, ResponseVideoList{
    private FragmentActivity mContext;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private VideoListAdapter videoListAdapter;
    private ArrayList<Video> videoList;

    private final View.OnClickListener videoItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildLayoutPosition(v);
            String videoId;
            String url = videoList.get(itemPosition).getVideoUrl();
            String[] urlArray = url.split("=");
            videoId = urlArray[2];
            openPlayVideoFragment(videoId);
        }
    };

    private void openPlayVideoFragment(String videoId) {
        Fragment playVideoFragment= new FragmentPlayYoutubeVideo();
        FragmentManager fragmentManager = mContext.getSupportFragmentManager();
        Bundle bundle =new Bundle();
        bundle.putString("videoId", videoId);
        playVideoFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.content_frame, playVideoFragment).commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_list_layout, container, false);
        return view;

    }

    private void setListener() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        initView();
        setListener();
        setData();
    }

    private void setData() {
        VideoListApi videoListApi = new VideoListApi(mContext);
        videoListApi.getVideoListApi(this, this);
        mLayoutManager = new LinearLayoutManager(mContext);
        videoListAdapter = new VideoListAdapter(mContext, videoList, videoItemClickListener);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(videoListAdapter);
    }

    private void initView() {
     recyclerView = (RecyclerView) mContext.findViewById(R.id.videolist_recyclerview);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void callResponse(Boolean response, String API_TAG) {

    }

    @Override
    public void responseVideos(ArrayList<Video> videoList) {
     this.videoList = videoList;
        videoListAdapter.videoList = videoList;
        videoListAdapter.notifyDataSetChanged();
    }
}
