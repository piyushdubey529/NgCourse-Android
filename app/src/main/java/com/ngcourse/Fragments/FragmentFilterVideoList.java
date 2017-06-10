package com.ngcourse.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ngcourse.R;
import com.ngcourse.adapter.VideoListAdapter;
import com.ngcourse.beans.Video;
import com.ngcourse.utilities.FontAwesome;

import java.util.ArrayList;

/**
 * Created by piyush on 10/6/17.
 */

public class FragmentFilterVideoList extends Fragment {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_list_layout, container, false);
        return view;
    }
}
