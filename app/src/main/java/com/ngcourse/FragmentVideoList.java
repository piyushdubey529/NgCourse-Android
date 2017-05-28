package com.ngcourse;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ngcourse.NetworkCall.NetworkCallResponse;
import com.ngcourse.Webservices.VideoListApi;
import com.ngcourse.adapter.VideoListAdapter;
import com.ngcourse.beans.Video;

import java.util.ArrayList;

/**
 * Created by piyush on 28/5/17.
 */

public class FragmentVideoList extends Fragment implements View.OnClickListener, NetworkCallResponse{
    private Button click;
    private FragmentActivity mContext;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private VideoListAdapter videoListAdapter;
    private ArrayList<Video> videoList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_list_layout, container, false);
        return view;

    }

    private void setListener() {
        click.setOnClickListener(this);
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
        mLayoutManager = new LinearLayoutManager(mContext);
        videoListAdapter = new VideoListAdapter(mContext, videoList);
    }

    private void initView() {
     recyclerView = (RecyclerView) mContext.findViewById(R.id.videolist_recyclerview);
    }

    @Override
    public void onClick(View v) {
        VideoListApi videoListApi = new VideoListApi(mContext);
        videoListApi.getVideoListApi(this);
    }

    @Override
    public void callResponse(Boolean response, String API_TAG) {

    }
}
