package com.ngcourse.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.ngcourse.NetworkCall.NetworkCallResponse;
import com.ngcourse.R;
import com.ngcourse.ResponseInterfaces.ResponseVideoList;
import com.ngcourse.Webservices.SearchVideoListApi;
import com.ngcourse.Webservices.VideoListApi;
import com.ngcourse.adapter.VideoListAdapter;
import com.ngcourse.beans.Video;
import com.ngcourse.utilities.AppProgress;
import com.ngcourse.utilities.FontAwesome;

import java.util.ArrayList;

/**
 * Created by piyush on 10/6/17.
 */

public class FragmentSearchVideoList extends Fragment implements NetworkCallResponse, ResponseVideoList{

    public static String API_TAG;
    private FragmentActivity mContext;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private VideoListAdapter videoListAdapter;
    private ArrayList<Video> videoList = new ArrayList<>();
    private FontAwesome filterIcon;
    private FontAwesome searchIcon;
    private Toolbar toolbar;
    private LinearLayout searchLayout;
    private EditText searchInput;
    private FontAwesome backButton;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_list_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        initView();
        setListener();
        setData();
    }

    private void setListener() {

    }

    private void setData() {
        Bundle bundle = getArguments();
        String keyword = bundle.getString("keyword");
        AppProgress.showProgress(mContext, progressBar);
        SearchVideoListApi searchVideoListApi = new SearchVideoListApi(mContext, keyword, "10", "10");
        searchVideoListApi.getSearchVideoListApi(this, this);
        mLayoutManager = new LinearLayoutManager(mContext);
        videoListAdapter = new VideoListAdapter(mContext, videoList);
        recyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(videoListAdapter);
    }

    private void initView() {
        recyclerView = (RecyclerView) mContext.findViewById(R.id.videolist_recyclerview);
        filterIcon = (FontAwesome) mContext.findViewById(R.id.filter_icon);
        searchIcon = (FontAwesome) mContext.findViewById(R.id.search_icon);
        toolbar = (Toolbar) mContext.findViewById(R.id.toolbar);
        searchLayout = (LinearLayout) mContext.findViewById(R.id.searchLayout);
        searchInput = (EditText) mContext.findViewById(R.id.searchInput);
        backButton = (FontAwesome) mContext.findViewById(R.id.backButton);
        searchIcon.setVisibility(View.VISIBLE);
        filterIcon.setVisibility(View.VISIBLE);
        progressBar = (ProgressBar) mContext.findViewById(R.id.progress_bar);
    }

    @Override
    public void callResponse(Boolean response, String API_TAG) {
      AppProgress.hideProgress(mContext, progressBar);
    }

    @Override
    public void responseVideos(ArrayList<Video> videoList) {
        this.videoList = videoList;
        videoListAdapter.videoList = this.videoList;
        videoListAdapter.notifyDataSetChanged();
    }
}
