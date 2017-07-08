package com.ngcourse.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ngcourse.NetworkCall.NetworkCallResponse;
import com.ngcourse.R;
import com.ngcourse.ResponseInterfaces.ResponseCourseList;
import com.ngcourse.Webservices.SearchCourseListApi;
import com.ngcourse.adapter.CourseListAdapter;
import com.ngcourse.beans.Course;
import com.ngcourse.utilities.AppProgress;
import com.ngcourse.utilities.FontAwesome;
import java.util.ArrayList;

/**
 * Created by piyush on 11/6/17.
 */

public class FragmentCourseSearchList extends Fragment implements View.OnClickListener, ResponseCourseList, NetworkCallResponse {
    public static String API_TAG;
    private FragmentActivity mContext;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CourseListAdapter courseListAdapter;
    private FontAwesome filterIcon;
    private FontAwesome searchIcon;
    private LinearLayout searchLayout;
    private EditText searchInput;
    private FontAwesome backButton;
    private Toolbar toolbar;
    private ArrayList<Course> courseList;
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
        setView();
    }

    private void setListener() {

    }

    private void setView() {
        Bundle bundle = getArguments();
        String keyword = bundle.getString("keyword");
        AppProgress.showProgress(mContext, progressBar);
        SearchCourseListApi searchCourseListApi = new SearchCourseListApi(keyword, "10", "0", mContext);
        searchCourseListApi.getSearchCourseListApi(this,this);
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        courseListAdapter = new CourseListAdapter(mContext, courseList);
        recyclerView.setAdapter(courseListAdapter);
    }

    private void initView() {
        recyclerView = (RecyclerView) mContext.findViewById(R.id.videolist_recyclerview);
        searchIcon = (FontAwesome) mContext.findViewById(R.id.search_icon);
        toolbar = (Toolbar) mContext.findViewById(R.id.toolbar);
        searchLayout = (LinearLayout) mContext.findViewById(R.id.searchLayout);
        searchInput = (EditText) mContext.findViewById(R.id.searchInput);
        backButton = (FontAwesome) mContext.findViewById(R.id.backButton);
        searchIcon.setVisibility(View.VISIBLE);
        progressBar = (ProgressBar) mContext.findViewById(R.id.progress_bar);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void callResponse(Boolean response, String API_TAG) {
      AppProgress.hideProgress(mContext, progressBar);
    }

    @Override
    public void responseCourses(ArrayList<Course> courseList) {
        this.courseList = courseList;
        courseListAdapter.courseList = courseList;
        courseListAdapter.notifyDataSetChanged();
    }
}
