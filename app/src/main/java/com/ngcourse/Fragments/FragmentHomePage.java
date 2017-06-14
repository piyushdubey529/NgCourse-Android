package com.ngcourse.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ngcourse.R;
import com.ngcourse.adapter.SlidingImageAdapter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by piyush on 12/6/17.
 */

public class FragmentHomePage extends Fragment {
    private ViewPager viewPager;
    private FragmentActivity mContext;
    private ArrayList<Integer> imageList = new ArrayList<>();
    private static final Integer[] Images = {R.drawable.firstimage,R.drawable.sliderimage1,
    R.drawable.sliderimage3, R.drawable.sliderimage4, R.drawable.sliderimage5, R.drawable.sliderimage6, R.drawable.sliderimage7};
    private int NUM_PAGES = Images.length;
    private int currentPage = 0;

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
    }

    private void initView() {
        viewPager = (ViewPager) mContext.findViewById(R.id.viewPager);
    }

}
