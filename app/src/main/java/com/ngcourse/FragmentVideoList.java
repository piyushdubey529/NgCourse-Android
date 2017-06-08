package com.ngcourse;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ngcourse.NetworkCall.NetworkCallResponse;
import com.ngcourse.ResponseInterfaces.ResponseVideoList;
import com.ngcourse.Webservices.FilterVideoListApi;
import com.ngcourse.Webservices.SearchVideoListApi;
import com.ngcourse.Webservices.VideoListApi;
import com.ngcourse.adapter.VideoListAdapter;
import com.ngcourse.beans.Video;
import com.ngcourse.utilities.FontAwesome;

import java.util.ArrayList;

/**
 * Created by piyush on 28/5/17.
 */

public class FragmentVideoList extends Fragment implements View.OnClickListener, NetworkCallResponse, ResponseVideoList,
        TextWatcher{

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
    private AlertDialog alertDialog;
    private FontAwesome cross;
    private Button applyFilter;
    private RadioGroup radioGroup;
    private View dialogView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_list_layout, container, false);
        return view;
    }

    private void setListener() {
        filterIcon.setOnClickListener(this);
        searchIcon.setOnClickListener(this);
        backButton.setOnClickListener(this);
        searchInput.addTextChangedListener(this);
        setScrollListener();
    }

    private void setScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = mLayoutManager.getItemCount();
                int lastVisibleItemCount = ((LinearLayoutManager)mLayoutManager).findLastVisibleItemPosition();
                if(totalItemCount > 0){
                    if((totalItemCount-1) == lastVisibleItemCount){
                        int skip = videoList.size();
                        VideoListApi videoListApi = new VideoListApi(String.valueOf(skip), "10", mContext);
                        videoListApi.getVideoListApi(FragmentVideoList.this, FragmentVideoList.this);
                    }
                }
            }
        });
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
        VideoListApi videoListApi = new VideoListApi("0", "10", mContext);
        videoListApi.getVideoListApi(this, this);
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
    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.filter_icon:
             openFilterDialog();
             break;
         case R.id.search_icon:
             toolbar.setVisibility(View.GONE);
             searchLayout.setVisibility(View.VISIBLE);
             searchInput.requestFocus();
             break;
         case R.id.backButton:
             searchLayout.setVisibility(View.GONE);
             toolbar.setVisibility(View.VISIBLE);
             break;
         case R.id.cross:
             alertDialog.dismiss();
             break;
         case R.id.apply:
             int  selectedId = radioGroup.getCheckedRadioButtonId();
             RadioButton radioButton = (RadioButton) dialogView.findViewById(selectedId);
             String keyword = radioButton.getText().toString();
             FilterVideoListApi filterVideoListApi = new FilterVideoListApi(mContext, keyword, "0", "10");
             filterVideoListApi.getFilterVideoListApi(this, this);
             break;
     }
    }

    private void openFilterDialog() {
        alertDialog = new AlertDialog.Builder(mContext).create();
        LayoutInflater inflater = LayoutInflater.from(mContext);
         dialogView = inflater.inflate(R.layout.filter_list_dialog, null);
        alertDialog.setView(dialogView);
        cross = (FontAwesome) dialogView.findViewById(R.id.cross);
        applyFilter = (Button) dialogView.findViewById(R.id.apply);
        radioGroup = (RadioGroup) dialogView.findViewById(R.id.radiogroup);
        cross.setOnClickListener(this);
        applyFilter.setOnClickListener(this);
        alertDialog.show();
    }

    @Override
    public void callResponse(Boolean response, String API_TAG) {
     this.API_TAG = API_TAG;
    }

    @Override
    public void responseVideos(ArrayList<Video> videoList) {
        if(this.API_TAG.equals("VideoListApi")){
            for(int i =0; i<videoList.size(); i++){
                Video video = videoList.get(i);
                if(!this.videoList.contains(video)) this.videoList.add(video);
            }
            videoListAdapter.videoList = this.videoList;
        }else{
            this.videoList = videoList;
        }
        videoListAdapter.notifyDataSetChanged();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        SearchVideoListApi searchVideoListApi = new SearchVideoListApi(mContext, s.toString(), "10", "0");
        searchVideoListApi.getSearchVideoListApi(this, this);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
