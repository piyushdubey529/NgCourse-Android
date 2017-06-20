package com.ngcourse.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ngcourse.NetworkCall.NetworkCallResponse;
import com.ngcourse.R;
import com.ngcourse.ResponseInterfaces.ResponseVideoList;
import com.ngcourse.Webservices.VideoListApi;
import com.ngcourse.adapter.SlidingImageAdapter;
import com.ngcourse.adapter.VideoListAdapter;
import com.ngcourse.beans.Video;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by piyush on 12/6/17.
 */

public class FragmentHomePage extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener,
        NetworkCallResponse, ResponseVideoList{
    private static  String API_TAG ;
    private ViewPager viewPager;
    private FragmentActivity mContext;
    private ArrayList<Integer> imageList = new ArrayList<>();
    private static final Integer[] Images = {R.drawable.firstimage,R.drawable.sliderimage1, R.drawable.sliderimage2,
    R.drawable.sliderimage4, R.drawable.sliderimage5, R.drawable.sliderimage6, R.drawable.sliderimage7};
    private int NUM_PAGES = Images.length;
    private int currentPage = 0;
    private ImageView imageViewMiddle;
    private ImageView imageViewBottom;
    private TextView textviewFirst;
    private TextView textviewSecond;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerViewAngularCourse;
    private RecyclerView.LayoutManager mLayoutManager;
    private VideoListAdapter videoListAdapter;
    private ArrayList<Video> videoList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_viewpager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        initView();
        setView();
        setTimer();
    }

    private void setTimer() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(currentPage == NUM_PAGES){
                            currentPage = 0;
                        }
                        viewPager.setCurrentItem(currentPage++, true);
                    }
                });
            }
        },1000, 3000);
    }

    private void setView() {
        for(int i=0; i<Images.length; i++){
            imageList.add(Images[i]);
        }
        viewPager.setAdapter(new SlidingImageAdapter(mContext, imageList));
        imageViewMiddle.setScaleType(ImageView.ScaleType.FIT_XY);
        imageViewBottom.setScaleType(ImageView.ScaleType.FIT_XY);
        textviewFirst.setText(R.string.text_first);
        textviewSecond.setText(R.string.text_second);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        VideoListApi videoListApi = new VideoListApi("0", "10", mContext);
        videoListApi.getVideoListApi(this, this);
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        videoListAdapter = new VideoListAdapter(mContext, videoList);
        recyclerViewAngularCourse.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewAngularCourse.getContext(), LinearLayoutManager.HORIZONTAL);
        recyclerViewAngularCourse.addItemDecoration(dividerItemDecoration);
        recyclerViewAngularCourse.setHasFixedSize(true);
        recyclerViewAngularCourse.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAngularCourse.setAdapter(videoListAdapter);
    }

    private void initView() {
        viewPager = (ViewPager) mContext.findViewById(R.id.viewPager);
        imageViewMiddle = (ImageView) mContext.findViewById(R.id.imageView1);
        imageViewBottom = (ImageView) mContext.findViewById(R.id.imageView2);
        textviewFirst = (TextView) mContext.findViewById(R.id.textview1);
        textviewSecond = (TextView) mContext.findViewById(R.id.textview2);
        bottomNavigationView = (BottomNavigationView) mContext.findViewById(R.id.bottomnavigation);
        recyclerViewAngularCourse = (RecyclerView) mContext.findViewById(R.id.angularcourserecyclerview);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.action_home:
                fragment = new FragmentHomePage();
                break;
            case R.id.action_videos:
                fragment = new FragmentVideoList();
                break;
            case R.id.action_course:
                fragment = new FragmentCourseList();
                break;
        }
        if(fragment != null){
            FragmentTransaction fragmentTransaction = mContext.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();
        }
        return false;
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
            videoListAdapter.videoList = this.videoList;
        }
        videoListAdapter.notifyDataSetChanged();
    }
}
